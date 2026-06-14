package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Вхідна модель для створення або оновлення спортивного центру")
public record SportCenterRequestDto(
        @Schema(description = "Назва спортивного центру", example = "Спорткомплекс Олімп", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Name is required")
        @Size(max = 150, message = "Name must be less than 150 characters")
        String name,

        @Schema(description = "Місто розташування спортивного центру", example = "Одеса", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "City is required")
        @Size(max = 100, message = "City must be less than 100 characters")
        String city,

        @Schema(description = "Адреса спортивного центру", example = "вул. Спортивна, 10", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Address is required")
        @Size(max = 255, message = "Address must be less than 255 characters")
        String address
) {
}
