package com.unifil.jogosEducativos.controllers;

import com.unifil.jogosEducativos.dto.GameStateResponseDTO;
import com.unifil.jogosEducativos.dto.StartGameRequestDTO;
import com.unifil.jogosEducativos.services.BlackjackService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
        origins = "http://localhost:5173",
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS
        },
        allowedHeaders = "*"
)
@RestController
@RequestMapping("/api/blackjack")
public class BlackjackController {

    private final BlackjackService service;

    public BlackjackController(BlackjackService service) {
        this.service = service;
    }

    @PostMapping("/start")
    public ResponseEntity<GameStateResponseDTO> start(@Valid @RequestBody StartGameRequestDTO request) {
        GameStateResponseDTO body = service.startGame(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
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
}
