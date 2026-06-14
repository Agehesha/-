package ua.opnu.labwork2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.opnu.labwork2.service.AnalyticsService;

import java.util.Map;

@RestController
@RequestMapping("/analytics")
@Tag(name = "Аналітика", description = "Аналітичні запити для підрахунку кількості користувачів, майданчиків, бронювань та завантаженості центрів")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @Operation(
            summary = "Порахувати майданчики",
            description = "Повертає загальну кількість спортивних майданчиків у системі."
    )
    @ApiResponse(responseCode = "200", description = "Кількість майданчиків отримано",
            content = @Content(schema = @Schema(implementation = Map.class)))
    @GetMapping("/fields/count")
    public Map<String, Long> getFieldsCount() {
        return Map.of("fieldsCount", analyticsService.getFieldsCount());
    }

    @Operation(
            summary = "Порахувати користувачів",
            description = "Повертає загальну кількість користувачів системи."
    )
    @ApiResponse(responseCode = "200", description = "Кількість користувачів отримано",
            content = @Content(schema = @Schema(implementation = Map.class)))
    @GetMapping("/users/count")
    public Map<String, Long> getUsersCount() {
        return Map.of("usersCount", analyticsService.getUsersCount());
    }

    @Operation(
            summary = "Порахувати активні бронювання",
            description = "Повертає кількість активних бронювань."
    )
    @ApiResponse(responseCode = "200", description = "Кількість активних бронювань отримано",
            content = @Content(schema = @Schema(implementation = Map.class)))
    @GetMapping("/bookings/active")
    public Map<String, Long> getActiveBookingsCount() {
        return Map.of("activeBookings", analyticsService.getActiveBookingsCount());
    }

    @Operation(
            summary = "Кількість майданчиків за типами",
            description = "Повертає кількість спортивних майданчиків, згруповану за типами."
    )
    @ApiResponse(responseCode = "200", description = "Статистика майданчиків за типами отримана",
            content = @Content(schema = @Schema(implementation = Map.class)))
    @GetMapping("/fields/by-type")
    public Map<String, Long> getFieldsByType() {
        return analyticsService.getFieldsByType();
    }

    @Operation(
            summary = "Завантаженість спортивних центрів",
            description = "Повертає кількість бронювань або майданчиків, пов'язаних із кожним спортивним центром."
    )
    @ApiResponse(responseCode = "200", description = "Статистика завантаженості центрів отримана",
            content = @Content(schema = @Schema(implementation = Map.class)))
    @GetMapping("/centers/workload")
    public Map<String, Long> getCentersWorkload() {
        return analyticsService.getCentersWorkload();
    }
}
