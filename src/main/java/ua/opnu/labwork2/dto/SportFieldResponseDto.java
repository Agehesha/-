package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(description = "Вихідна модель спортивного майданчика")
public record SportFieldResponseDto(
        @Schema(description = "Унікальний ідентифікатор майданчика", example = "1")
        Long id,

        @Schema(description = "Назва спортивного майданчика", example = "Футбольне поле №1")
        String name,

        @Schema(description = "Локація майданчика", example = "Сектор A, біля головного входу")
        String location,

        @Schema(description = "Місткість майданчика", example = "22")
        Integer capacity,

        @Schema(description = "Ідентифікатор спортивного центру", example = "1")
        Long sportCenterId,

        @Schema(description = "Назва спортивного центру", example = "Спорткомплекс Олімп")
        String sportCenterName,

        @Schema(description = "Типи, до яких належить майданчик")
        Set<FieldTypeResponseDto> fieldTypes
) {
}
