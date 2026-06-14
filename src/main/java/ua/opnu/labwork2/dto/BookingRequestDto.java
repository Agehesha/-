package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import ua.opnu.labwork2.enums.BookingStatus;

import java.time.LocalDateTime;

@Schema(description = "Вхідна модель для створення або оновлення бронювання")
public record BookingRequestDto(
        @Schema(description = "Дата та час початку бронювання", example = "2026-06-01T18:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Start time is required")
        LocalDateTime startTime,

        @Schema(description = "Дата та час завершення бронювання", example = "2026-06-01T19:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "End time is required")
        LocalDateTime endTime,

        @Schema(description = "Статус бронювання", example = "CREATED")
        BookingStatus status,

        @Schema(description = "Ідентифікатор користувача, який створює бронювання", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "User id is required")
        Long userId,

        @Schema(description = "Ідентифікатор майданчика, який бронюється", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Sport field id is required")
        Long sportFieldId,

        @Schema(description = "Ідентифікатор спортивного центру", example = "1")
        Long sportCenterId
) {
}
