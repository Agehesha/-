package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Вихідна модель користувача")
public record UserResponseDto(
        @Schema(description = "Унікальний ідентифікатор користувача", example = "1")
        Long id,

        @Schema(description = "Ім'я користувача", example = "Роман")
        String firstName,

        @Schema(description = "Прізвище користувача", example = "Шугаєв")
        String lastName,

        @Schema(description = "Електронна пошта користувача", example = "roman.shugaev@example.com")
        String email,

        @Schema(description = "Контактний номер телефону користувача", example = "+380501234567")
        String phone
) {
}
