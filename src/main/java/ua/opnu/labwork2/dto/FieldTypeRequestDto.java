package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Вхідна модель для створення типу спортивного майданчика")
public record FieldTypeRequestDto(
        @Schema(description = "Унікальна назва типу майданчика", example = "Футбольне поле", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must be less than 100 characters")
        String name,

        @Schema(description = "Опис типу майданчика", example = "Майданчик для проведення футбольних тренувань і матчів")
        @Size(max = 1000, message = "Description must be less than 1000 characters")
        String description
) {
}
