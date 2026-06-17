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
    private final Map<String, Integer> playerBalances;

    public BlackjackService(BlackjackGameRepository repository) {
        this.repository = repository;
        this.inMemoryGames = new ConcurrentHashMap<>();
        this.playerBalances = new ConcurrentHashMap<>();
    }

    public GameStateResponseDTO startGame(StartGameRequestDTO request) {
        String gameId = UUID.randomUUID().toString();
        String playerName = request.playerName() == null || request.playerName().isBlank()
                ? "Player 1"
                : request.playerName();
        int betAmount = request.betAmount();

        int balance = playerBalances.getOrDefault(playerName, 1500);
        if (betAmount <= 0) {
            throw new IllegalArgumentException("A aposta deve ser maior que zero.");
        }
        if (betAmount > balance) {
            throw new IllegalArgumentException("Saldo insuficiente para essa aposta.");
        }

        balance -= betAmount;
        playerBalances.put(playerName, balance);

        BlackjackGame game = new BlackjackGame(gameId, playerName, betAmount);
        game.startRound();

        inMemoryGames.put(gameId, game);

        String result = game.isFinished() ? game.gameResult() : "IN_PROGRESS";
        saveSnapshot(game, balance, result);

        return toDto(game, balance, result);
    }

    public GameStateResponseDTO hit(String gameId) {
        BlackjackGame game = getGameOrThrow(gameId);
        if (game.isFinished()) {
            throw new IllegalStateException("Rodada encerrada. Não é possível pedir carta (hit).");
        }

        game.hitPlayer();

        int balance = playerBalances.getOrDefault(game.getPlayer().getName(), 0);
        String status = game.isFinished() ? game.gameResult() : "IN_PROGRESS";

        if (game.isFinished()) {
            balance = adjustBalance(game, balance);
            playerBalances.put(game.getPlayer().getName(), balance);
        }

        saveSnapshot(game, balance, status);
        return toDto(game, balance, status);
    }

    public GameStateResponseDTO stand(String gameId) {
        BlackjackGame game = getGameOrThrow(gameId);
        if (game.isFinished()) {
            throw new IllegalStateException("Rodada encerrada. Não é possível parar novamente (stand).");
        }

        game.standPlayer();

        int balance = playerBalances.getOrDefault(game.getPlayer().getName(), 0);
        balance = adjustBalance(game, balance);
        playerBalances.put(game.getPlayer().getName(), balance);

        String status = game.gameResult();
        saveSnapshot(game, balance, status);

        return toDto(game, balance, status);
    }

    public GameStateResponseDTO getState(String gameId) {
        BlackjackGame game = getGameOrThrow(gameId);
        int balance = playerBalances.getOrDefault(game.getPlayer().getName(), 0);
        String status = game.isFinished() ? game.gameResult() : "IN_PROGRESS";
        return toDto(game, balance, status);
    }

    public int deposit(String playerName, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Valor de depósito inválido.");
        }
        int balance = playerBalances.getOrDefault(playerName, 1500);
        balance += amount;
        playerBalances.put(playerName, balance);
        return balance;
    }

    private int adjustBalance(BlackjackGame game, int balance) {
        String result = game.gameResult();
        if ("PLAYER_WIN".equals(result)) {
            balance += game.getBetAmount() * 2;
        } else if ("DRAW".equals(result)) {
            balance += game.getBetAmount();
        }
        return balance;
    }

    private BlackjackGame getGameOrThrow(String gameId) {
        BlackjackGame game = inMemoryGames.get(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Partida não encontrada: " + gameId);
        }
        return game;
    }

    public int getStateForNewPlayer(String playerName) {
        return playerBalances.getOrDefault(playerName, 1500);
    }

    private void saveSnapshot(BlackjackGame game, int balance, String status) {
        repository.findByGameId(game.getGameId()).ifPresentOrElse(existing -> {
            existing.setPlayerName(game.getPlayer().getName());
            existing.setPlayerScore(game.getPlayer().calculateScore());
            existing.setDealerScore(game.getDealer().calculateScore());
            existing.setStatus(status);
            existing.setBalance(balance);
            existing.setBetAmount(game.getBetAmount());
            existing.setPlayerCards(new java.util.ArrayList<>(game.getPlayerCards()));
            existing.setDealerCards(new java.util.ArrayList<>(game.getDealerCards()));
            repository.save(existing);
        }, () -> {
            BlackjackGameEntity entity = new BlackjackGameEntity();
            entity.setGameId(game.getGameId());
            entity.setPlayerName(game.getPlayer().getName());
            entity.setPlayerScore(game.getPlayer().calculateScore());
            entity.setDealerScore(game.getDealer().calculateScore());
            entity.setStatus(status);
            entity.setBalance(balance);
            entity.setBetAmount(game.getBetAmount());
            entity.setPlayerCards(new java.util.ArrayList<>(game.getPlayerCards()));
            entity.setDealerCards(new java.util.ArrayList<>(game.getDealerCards()));
            repository.save(entity);
        });
    }

    private GameStateResponseDTO toDto(BlackjackGame game, int balance, String status) {
        return new GameStateResponseDTO(
                game.getGameId(),
                game.getPlayer().getName(),
                game.getPlayerCards(),
                game.getDealerCards(),
                game.getPlayer().calculateScore(),
                game.getDealer().calculateScore(),
                game.getBetAmount(),
                balance,
                game.isFinished(),
                game.isFinished() ? status : null
        );
    }
}