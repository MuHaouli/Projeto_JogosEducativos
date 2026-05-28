package com.unifil.jogosEducativos.controllers;

import com.unifil.jogosEducativos.dto.GameStateResponseDTO;
import com.unifil.jogosEducativos.dto.StartGameRequestDTO;
import com.unifil.jogosEducativos.services.BlackjackService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blackjack")
public class BlackjackController {

    private final BlackjackService service;

    public BlackjackController(BlackjackService service) {
        this.service = service;
    }

    @PostMapping("/start")
    public GameStateResponseDTO start(@Valid @RequestBody StartGameRequestDTO request) {
        /*TODO retornar ResponseEntity com status HTTP explicito (ex: 201 para start)*/
        return service.startGame(request);
    }

    @PostMapping("/{gameId}/hit")
    public GameStateResponseDTO hit(@PathVariable String gameId) {
        //TODO validar formato do gameId e padronizar erros de entrada
        return service.hit(gameId);
    }

    @PostMapping("/{gameId}/stand")
    public GameStateResponseDTO stand(@PathVariable String gameId) {
        // TODO adicionar endpoint GET /{gameId} para consulta do estado sem alterar rodada
        return service.stand(gameId);
    }
}
