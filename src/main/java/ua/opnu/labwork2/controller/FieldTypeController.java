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
import ua.opnu.labwork2.dto.FieldTypeRequestDto;
import ua.opnu.labwork2.dto.FieldTypeResponseDto;
import ua.opnu.labwork2.mapper.EntityMapper;
import ua.opnu.labwork2.service.FieldTypeService;

import java.util.List;

@RestController
@RequestMapping("/field-types")
@Tag(name = "Типи майданчиків", description = "Операції для керування типами спортивних майданчиків")
public class FieldTypeController {

    private final FieldTypeService fieldTypeService;
    private final EntityMapper mapper;

    public FieldTypeController(FieldTypeService fieldTypeService, EntityMapper mapper) {
        this.fieldTypeService = fieldTypeService;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Створити тип майданчика",
            description = "Створює тип спортивного майданчика. Назва типу є обов'язковою та має бути унікальною."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Тип майданчика створено",
                    content = @Content(schema = @Schema(implementation = FieldTypeResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні дані типу майданчика",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Тип із такою назвою вже існує",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<FieldTypeResponseDto> createFieldType(@Valid @RequestBody FieldTypeRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toFieldTypeResponse(fieldTypeService.createFieldType(dto)));
    }

    @Operation(
            summary = "Отримати типи майданчиків",
            description = "Повертає список доступних типів спортивних майданчиків."
    )
    @ApiResponse(responseCode = "200", description = "Список типів отримано",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = FieldTypeResponseDto.class))))
    @GetMapping
    public List<FieldTypeResponseDto> getAllFieldTypes() {
        return fieldTypeService.getAllFieldTypes().stream().map(mapper::toFieldTypeResponse).toList();
    }

    @Operation(
            summary = "Отримати тип за id",
            description = "Повертає тип спортивного майданчика за його ідентифікатором."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Тип знайдено",
                    content = @Content(schema = @Schema(implementation = FieldTypeResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Тип з таким id не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public FieldTypeResponseDto getFieldTypeById(@Parameter(description = "Ідентифікатор типу майданчика", example = "1") @PathVariable Long id) {
        return mapper.toFieldTypeResponse(fieldTypeService.getFieldTypeById(id));
    }

    @Operation(
            summary = "Видалити тип майданчика",
            description = "Видаляє тип спортивного майданчика за id. Якщо тип не існує, повертається 404."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Тип видалено"),
            @ApiResponse(responseCode = "404", description = "Тип з таким id не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFieldType(@Parameter(description = "Ідентифікатор типу майданчика", example = "1") @PathVariable Long id) {
        fieldTypeService.deleteFieldType(id);
        return ResponseEntity.noContent().build();
    }
}
