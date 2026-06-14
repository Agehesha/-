package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Вхідна модель для створення або оновлення користувача")
public record UserRequestDto(
        @Schema(description = "Ім'я користувача", example = "Роман", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "First name is required")
        @Size(max = 100, message = "First name must be less than 100 characters")
        String firstName,

        @Schema(description = "Прізвище користувача", example = "Шугаєв", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Last name is required")
        @Size(max = 100, message = "Last name must be less than 100 characters")
        String lastName,

        @Schema(description = "Унікальна електронна пошта користувача", example = "roman.shugaev@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @Schema(description = "Контактний номер телефону користувача", example = "+380501234567", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Phone is required")
        @Size(max = 30, message = "Phone must be less than 30 characters")
        String phone
) {
}
