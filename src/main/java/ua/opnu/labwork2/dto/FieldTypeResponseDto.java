package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Вихідна модель типу спортивного майданчика")
public record FieldTypeResponseDto(
        @Schema(description = "Унікальний ідентифікатор типу", example = "1")
        Long id,

        @Schema(description = "Назва типу майданчика", example = "Футбольне поле")
        String name,

        @Schema(description = "Опис типу майданчика", example = "Майданчик для проведення футбольних тренувань і матчів")
        String description
) {
}
