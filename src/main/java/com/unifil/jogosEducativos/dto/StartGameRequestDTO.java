package com.unifil.jogosEducativos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StartGameRequestDTO(
        @NotBlank(message = "playerName e obrigatorio")
        @Size(min = 2, max = 30, message = "playerName deve ter entre 2 e 30 caracteres")
        String playerName
) {
}
