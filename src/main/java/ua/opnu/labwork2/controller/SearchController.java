package ua.opnu.labwork2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.opnu.labwork2.dto.SportCenterResponseDto;
import ua.opnu.labwork2.dto.SportFieldResponseDto;
import ua.opnu.labwork2.dto.UserResponseDto;
import ua.opnu.labwork2.mapper.EntityMapper;
import ua.opnu.labwork2.service.SportCenterService;
import ua.opnu.labwork2.service.SportFieldService;
import ua.opnu.labwork2.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/search")
@Tag(name = "Пошук", description = "Повнотекстовий пошук користувачів, спортивних центрів і майданчиків")
public class SearchController {

    private final SportFieldService sportFieldService;
    private final UserService userService;
    private final SportCenterService sportCenterService;
    private final EntityMapper mapper;

    public SearchController(SportFieldService sportFieldService,
                            UserService userService,
                            SportCenterService sportCenterService,
                            EntityMapper mapper) {
        this.sportFieldService = sportFieldService;
        this.userService = userService;
        this.sportCenterService = sportCenterService;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Пошук майданчиків",
            description = "Шукає спортивні майданчики за текстовим запитом у назві або локації."
    )
    @ApiResponse(responseCode = "200", description = "Результати пошуку майданчиків отримано",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SportFieldResponseDto.class))))
    @GetMapping("/fields")
    public List<SportFieldResponseDto> searchFields(@Parameter(description = "Пошуковий запит", example = "футбол") @RequestParam String query) {
        return sportFieldService.searchFields(query).stream().map(mapper::toSportFieldResponse).toList();
    }

    @Operation(
            summary = "Пошук користувачів",
            description = "Шукає користувачів за ім'ям, прізвищем або електронною поштою."
    )
    @ApiResponse(responseCode = "200", description = "Результати пошуку користувачів отримано",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class))))
    @GetMapping("/users")
    public List<UserResponseDto> searchUsers(@Parameter(description = "Пошуковий запит", example = "roman") @RequestParam String query) {
        return userService.searchUsers(query).stream().map(mapper::toUserResponse).toList();
    }

    @Operation(
            summary = "Пошук спортивних центрів",
            description = "Шукає спортивні центри за назвою, містом або адресою."
    )
    @ApiResponse(responseCode = "200", description = "Результати пошуку спортивних центрів отримано",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SportCenterResponseDto.class))))
    @GetMapping("/centers")
    public List<SportCenterResponseDto> searchCenters(@Parameter(description = "Пошуковий запит", example = "Одеса") @RequestParam String query) {
        return sportCenterService.searchCenters(query).stream().map(mapper::toSportCenterResponse).toList();
    }
}
