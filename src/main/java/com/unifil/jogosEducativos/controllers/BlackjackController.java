package com.unifil.jogosEducativos.controllers;

import com.unifil.jogosEducativos.dto.GameStateResponseDTO;
import com.unifil.jogosEducativos.dto.StartGameRequestDTO;
import com.unifil.jogosEducativos.services.BlackjackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/blackjack")
@CrossOrigin(origins = "http://localhost:5173")
public class BlackjackController {

    private final BlackjackService service;

    public BlackjackController(BlackjackService service) {
        this.service = service;
    }

    @PostMapping("/start")
    public ResponseEntity<GameStateResponseDTO> start(@Valid @RequestBody StartGameRequestDTO request) {
        GameStateResponseDTO body = service.startGame(request);
        return ResponseEntity.status(201).body(body);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameStateResponseDTO> getState(@PathVariable String gameId) {
        GameStateResponseDTO body = service.getState(gameId);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/{gameId}/hit")
    public ResponseEntity<GameStateResponseDTO> hit(@PathVariable String gameId) {
        GameStateResponseDTO body = service.hit(gameId);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/{gameId}/stand")
    public ResponseEntity<GameStateResponseDTO> stand(@PathVariable String gameId) {
        GameStateResponseDTO body = service.stand(gameId);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/{playerName}/deposit")
    public ResponseEntity<Map<String, Integer>> deposit(
            @PathVariable String playerName,
            @RequestBody Map<String, Integer> body) {

        Integer amount = body.get("amount");
        if (amount == null || amount <= 0) {
            return ResponseEntity.badRequest().body(Map.of("balance", service.getStateForNewPlayer(playerName)));
        }
        int newBalance = service.deposit(playerName, amount);
        return ResponseEntity.ok(Map.of("balance", newBalance));
    }
}