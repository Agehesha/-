package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Вихідна модель спортивного центру")
public record SportCenterResponseDto(
        @Schema(description = "Унікальний ідентифікатор спортивного центру", example = "1")
        Long id,

        @Schema(description = "Назва спортивного центру", example = "Спорткомплекс Олімп")
        String name,

        @Schema(description = "Місто розташування", example = "Одеса")
        String city,

        @Schema(description = "Адреса спортивного центру", example = "вул. Спортивна, 10")
        String address
) {
}
