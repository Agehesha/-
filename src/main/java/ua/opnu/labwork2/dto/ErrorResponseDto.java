package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Стандартна структура відповіді сервера у разі помилки")
public record ErrorResponseDto(
        @Schema(description = "Дата та час виникнення помилки", example = "2026-06-01T18:05:00")
        LocalDateTime timestamp,

        @Schema(description = "HTTP-статус помилки", example = "400")
        int status,

        @Schema(description = "Коротка назва помилки", example = "Bad Request")
        String error,

        @Schema(description = "Детальне повідомлення про помилку", example = "Validation failed")
        String message,

        @Schema(description = "Шлях запиту, під час обробки якого сталася помилка", example = "/users")
        String path,

        @Schema(description = "Мапа помилок валідації, де ключ — назва поля, значення — текст помилки",
                example = "{\"email\":\"Email must be valid\"}")
        Map<String, String> validationErrors
) {
}
