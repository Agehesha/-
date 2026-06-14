package ua.opnu.labwork2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.labwork2.dto.ErrorResponseDto;
import ua.opnu.labwork2.dto.SportCenterRequestDto;
import ua.opnu.labwork2.dto.SportCenterResponseDto;
import ua.opnu.labwork2.dto.SportFieldResponseDto;
import ua.opnu.labwork2.mapper.EntityMapper;
import ua.opnu.labwork2.service.SportCenterService;

import java.util.List;

@RestController
@RequestMapping("/centers")
@Tag(name = "Спортивні центри", description = "Операції для керування спортивними центрами та перегляду їхніх майданчиків")
public class SportCenterController {

    private final SportCenterService sportCenterService;
    private final EntityMapper mapper;

    public SportCenterController(SportCenterService sportCenterService, EntityMapper mapper) {
        this.sportCenterService = sportCenterService;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Створити спортивний центр",
            description = "Створює спортивний центр. Назва, місто та адреса є обов'язковими і проходять перевірку довжини."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Спортивний центр створено",
                    content = @Content(schema = @Schema(implementation = SportCenterResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні дані спортивного центру",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<SportCenterResponseDto> createCenter(@Valid @RequestBody SportCenterRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toSportCenterResponse(sportCenterService.createCenter(dto)));
    }

    @Operation(
            summary = "Отримати спортивні центри",
            description = "Повертає список усіх спортивних центрів, зареєстрованих у системі."
    )
    @ApiResponse(responseCode = "200", description = "Список спортивних центрів отримано",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SportCenterResponseDto.class))))
    @GetMapping
    public List<SportCenterResponseDto> getAllCenters() {
        return sportCenterService.getAllCenters().stream().map(mapper::toSportCenterResponse).toList();
    }

    @Operation(
            summary = "Отримати спортивний центр",
            description = "Повертає спортивний центр за його ідентифікатором."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Спортивний центр знайдено",
                    content = @Content(schema = @Schema(implementation = SportCenterResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Спортивний центр з таким id не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public SportCenterResponseDto getCenterById(@Parameter(description = "Ідентифікатор спортивного центру", example = "1") @PathVariable Long id) {
        return mapper.toSportCenterResponse(sportCenterService.getCenterById(id));
    }

    @Operation(
            summary = "Оновити спортивний центр",
            description = "Оновлює назву, місто та адресу спортивного центру. Усі обов'язкові поля перевіряються валідацією."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Спортивний центр оновлено",
                    content = @Content(schema = @Schema(implementation = SportCenterResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні дані спортивного центру",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Спортивний центр з таким id не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{id}")
    public SportCenterResponseDto updateCenter(@Parameter(description = "Ідентифікатор спортивного центру", example = "1") @PathVariable Long id,
                                               @Valid @RequestBody SportCenterRequestDto dto) {
        return mapper.toSportCenterResponse(sportCenterService.updateCenter(id, dto));
    }

    @Operation(
            summary = "Видалити спортивний центр",
            description = "Видаляє спортивний центр за id. Якщо центр не існує, повертається 404."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Спортивний центр видалено"),
            @ApiResponse(responseCode = "404", description = "Спортивний центр з таким id не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCenter(@Parameter(description = "Ідентифікатор спортивного центру", example = "1") @PathVariable Long id) {
        sportCenterService.deleteCenter(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Отримати майданчики центру",
            description = "Повертає спортивні майданчики, які належать конкретному спортивному центру."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Майданчики центру отримано",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SportFieldResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "Спортивний центр з таким id не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}/fields")
    public List<SportFieldResponseDto> getFieldsByCenter(@Parameter(description = "Ідентифікатор спортивного центру", example = "1") @PathVariable Long id) {
        return sportCenterService.getFieldsByCenterId(id).stream().map(mapper::toSportFieldResponse).toList();
    }
}
