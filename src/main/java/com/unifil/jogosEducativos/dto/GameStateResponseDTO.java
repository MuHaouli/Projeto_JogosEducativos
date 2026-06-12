package com.unifil.jogosEducativos.dto;

import java.util.List;
public record GameStateResponseDTO(
        String gameId,
        String playerName,
        List<String> playerCards,
        List<String> dealerCards,
        int playerScore,
        int dealerScore,
        boolean finished,
        String result
) {}