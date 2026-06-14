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
import ua.opnu.labwork2.dto.SportFieldRequestDto;
import ua.opnu.labwork2.dto.SportFieldResponseDto;
import ua.opnu.labwork2.mapper.EntityMapper;
import ua.opnu.labwork2.service.SportFieldService;

import java.util.List;

@RestController
@RequestMapping("/fields")
@Tag(name = "Спортивні майданчики", description = "Операції для керування спортивними майданчиками та прив'язкою типів майданчиків")
public class SportFieldController {

    private final SportFieldService sportFieldService;
    private final EntityMapper mapper;

    public SportFieldController(SportFieldService sportFieldService, EntityMapper mapper) {
        this.sportFieldService = sportFieldService;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Створити майданчик",
            description = "Створює спортивний майданчик у заданому спортивному центрі. Назва, локація, місткість і id центру є обов'язковими."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Майданчик створено",
                    content = @Content(schema = @Schema(implementation = SportFieldResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні дані майданчика",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Спортивний центр для майданчика не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<SportFieldResponseDto> createField(@Valid @RequestBody SportFieldRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toSportFieldResponse(sportFieldService.createField(dto)));
    }

    @Operation(
            summary = "Отримати майданчики",
            description = "Повертає список усіх спортивних майданчиків із інформацією про центр і типи майданчика."
    )
    @ApiResponse(responseCode = "200", description = "Список майданчиків отримано",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SportFieldResponseDto.class))))
    @GetMapping
    public List<SportFieldResponseDto> getAllFields() {
        return sportFieldService.getAllFields().stream().map(mapper::toSportFieldResponse).toList();
    }

    @Operation(
            summary = "Отримати майданчик",
            description = "Повертає спортивний майданчик за його ідентифікатором."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Майданчик знайдено",
                    content = @Content(schema = @Schema(implementation = SportFieldResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Майданчик з таким id не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public SportFieldResponseDto getFieldById(@Parameter(description = "Ідентифікатор майданчика", example = "1") @PathVariable Long id) {
        return mapper.toSportFieldResponse(sportFieldService.getFieldById(id));
    }

    @Operation(
            summary = "Оновити майданчик",
            description = "Оновлює назву, локацію, місткість і спортивний центр майданчика. Місткість повинна бути більшою за 0."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Майданчик оновлено",
                    content = @Content(schema = @Schema(implementation = SportFieldResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні дані майданчика",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Майданчик або спортивний центр не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{id}")
    public SportFieldResponseDto updateField(@Parameter(description = "Ідентифікатор майданчика", example = "1") @PathVariable Long id,
                                             @Valid @RequestBody SportFieldRequestDto dto) {
        return mapper.toSportFieldResponse(sportFieldService.updateField(id, dto));
    }

    @Operation(
            summary = "Видалити майданчик",
            description = "Видаляє спортивний майданчик за id. Якщо майданчика не існує, повертається 404."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Майданчик видалено"),
            @ApiResponse(responseCode = "404", description = "Майданчик з таким id не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteField(@Parameter(description = "Ідентифікатор майданчика", example = "1") @PathVariable Long id) {
        sportFieldService.deleteField(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Отримати майданчики центру",
            description = "Повертає всі майданчики, що належать спортивному центру з переданим id."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список майданчиків спортивного центру отримано",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SportFieldResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "Спортивний центр з таким id не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/center/{centerId}")
    public List<SportFieldResponseDto> getFieldsByCenter(@Parameter(description = "Ідентифікатор спортивного центру", example = "1") @PathVariable Long centerId) {
        return sportFieldService.getFieldsByCenterId(centerId).stream().map(mapper::toSportFieldResponse).toList();
    }

    @Operation(
            summary = "Додати тип до майданчика",
            description = "Додає тип майданчика до конкретного спортивного майданчика. Майданчик і тип повинні існувати."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Тип додано до майданчика",
                    content = @Content(schema = @Schema(implementation = SportFieldResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Майданчик або тип не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/{id}/types/{typeId}")
    public SportFieldResponseDto addTypeToField(@Parameter(description = "Ідентифікатор майданчика", example = "1") @PathVariable Long id,
                                                @Parameter(description = "Ідентифікатор типу майданчика", example = "2") @PathVariable Long typeId) {
        return mapper.toSportFieldResponse(sportFieldService.addTypeToField(id, typeId));
    }

    @Operation(
            summary = "Видалити тип у майданчика",
            description = "Прибирає зв'язок між майданчиком і типом. Майданчик і тип повинні існувати."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Тип видалено у майданчика",
                    content = @Content(schema = @Schema(implementation = SportFieldResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Майданчик або тип не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}/types/{typeId}")
    public SportFieldResponseDto removeTypeFromField(@Parameter(description = "Ідентифікатор майданчика", example = "1") @PathVariable Long id,
                                                     @Parameter(description = "Ідентифікатор типу майданчика", example = "2") @PathVariable Long typeId) {
        return mapper.toSportFieldResponse(sportFieldService.removeTypeFromField(id, typeId));
    }
}
