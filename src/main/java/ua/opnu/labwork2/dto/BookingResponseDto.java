package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ua.opnu.labwork2.enums.BookingStatus;

import java.time.LocalDateTime;

@Schema(description = "Вихідна модель бронювання")
public record BookingResponseDto(
        @Schema(description = "Унікальний ідентифікатор бронювання", example = "1")
        Long id,

        @Schema(description = "Дата та час початку бронювання", example = "2026-06-01T18:00:00")
        LocalDateTime startTime,

        @Schema(description = "Дата та час завершення бронювання", example = "2026-06-01T19:30:00")
        LocalDateTime endTime,

        @Schema(description = "Поточний статус бронювання", example = "CONFIRMED")
        BookingStatus status,

        @Schema(description = "Ідентифікатор користувача", example = "1")
        Long userId,

        @Schema(description = "Повне ім'я користувача", example = "Роман Шугаєв")
        String userFullName,

        @Schema(description = "Ідентифікатор майданчика", example = "1")
        Long sportFieldId,

        @Schema(description = "Назва майданчика", example = "Футбольне поле №1")
        String sportFieldName,

        @Schema(description = "Ідентифікатор спортивного центру", example = "1")
        Long sportCenterId,

        @Schema(description = "Назва спортивного центру", example = "Спорткомплекс Олімп")
        String sportCenterName
) {
}
