package com.unifil.jogosEducativos.services;

import com.unifil.jogosEducativos.dto.GameStateResponseDTO;
import com.unifil.jogosEducativos.dto.StartGameRequestDTO;
import com.unifil.jogosEducativos.entities.BlackjackGameEntity;
import com.unifil.jogosEducativos.models.BlackjackGame;
import com.unifil.jogosEducativos.repositories.BlackjackGameRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BlackjackService {

    private final BlackjackGameRepository repository;
    private final Map<String, BlackjackGame> inMemoryGames;

    public BlackjackService(BlackjackGameRepository repository) {
        this.repository = repository;
        this.inMemoryGames = new ConcurrentHashMap<>();
    }

    public GameStateResponseDTO startGame(StartGameRequestDTO request) {
        // TODO mover criacao de gameId para estrategia/gerador dedicado se quiser testar melhor
        String gameId = UUID.randomUUID().toString();
        String playerName = request.playerName() == null || request.playerName().isBlank()
                ? "Player 1"
                : request.playerName();

        BlackjackGame game = new BlackjackGame(gameId, playerName);
        game.startRound();

        inMemoryGames.put(gameId, game);
        saveSnapshot(game, "STARTED");

        return toResponse(game, "IN_PROGRESS");
    }

    public GameStateResponseDTO hit(String gameId) {
        BlackjackGame game = getGameOrThrow(gameId);
        if (game.isFinished()) {
            throw new IllegalStateException("Rodada encerrada. Nao e possivel pedir carta (hit).");
        }
        game.hitPlayer();

        String result = game.isFinished() ? game.gameResult() : "IN_PROGRESS";
        saveSnapshot(game, result);

        return toResponse(game, result);
    }

    public GameStateResponseDTO stand(String gameId) {
        BlackjackGame game = getGameOrThrow(gameId);
        if (game.isFinished()) {
            throw new IllegalStateException("Rodada encerrada. Nao e possivel parar novamente (stand).");
        }
        game.standPlayer();

        String result = game.gameResult();
        saveSnapshot(game, result);

        return toResponse(game, result);
    }

    public GameStateResponseDTO getState(String gameId) {
        BlackjackGame game = getGameOrThrow(gameId);
        String result = game.isFinished() ? game.gameResult() : "IN_PROGRESS";
        return toResponse(game, result);
    }

    private BlackjackGame getGameOrThrow(String gameId) {
        BlackjackGame game = inMemoryGames.get(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Partida nao encontrada: " + gameId);
        }
        return game;
    }

    private void saveSnapshot(BlackjackGame game, String status) {
        repository.findByGameId(game.getGameId()).ifPresentOrElse(existing -> {
            existing.setPlayerName(game.getPlayer().getName());
            existing.setPlayerScore(game.getPlayer().calculateScore());
            existing.setDealerScore(game.getDealer().calculateScore());
            existing.setStatus(status);
            repository.save(existing);
        }, () -> {
            BlackjackGameEntity entity = new BlackjackGameEntity();
            entity.setGameId(game.getGameId());
            entity.setPlayerName(game.getPlayer().getName());
            entity.setPlayerScore(game.getPlayer().calculateScore());
            entity.setDealerScore(game.getDealer().calculateScore());
            entity.setStatus(status);
            repository.save(entity);
        });
    }

    private GameStateResponseDTO toResponse(BlackjackGame game, String result) {
        // TODO expor campo de status da rodada separado de result (ex: IN_PROGRESS, FINISHED)
        return new GameStateResponseDTO(
                game.getGameId(),
                game.getPlayer().getName(),
                game.getPlayerCards(),
                game.getDealerCards(),
                game.getPlayer().calculateScore(),
                game.getDealer().calculateScore(),
                game.isFinished(),
                result
        );
    }
}
