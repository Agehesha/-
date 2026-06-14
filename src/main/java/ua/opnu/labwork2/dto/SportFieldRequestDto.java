package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Вхідна модель для створення або оновлення спортивного майданчика")
public record SportFieldRequestDto(
        @Schema(description = "Назва спортивного майданчика", example = "Футбольне поле №1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Name is required")
        @Size(max = 150, message = "Name must be less than 150 characters")
        String name,

        @Schema(description = "Локація або короткий опис місця розташування", example = "Сектор A, біля головного входу", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Location is required")
        @Size(max = 255, message = "Location must be less than 255 characters")
        String location,

        @Schema(description = "Максимальна кількість учасників", example = "22", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Capacity is required")
        @Min(value = 1, message = "Capacity must be greater than 0")
        Integer capacity,

        @Schema(description = "Ідентифікатор спортивного центру, до якого належить майданчик", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Sport center id is required")
        Long sportCenterId
) {
}
