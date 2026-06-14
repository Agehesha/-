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
import ua.opnu.labwork2.dto.BookingResponseDto;
import ua.opnu.labwork2.dto.ErrorResponseDto;
import ua.opnu.labwork2.dto.UserRequestDto;
import ua.opnu.labwork2.dto.UserResponseDto;
import ua.opnu.labwork2.mapper.EntityMapper;
import ua.opnu.labwork2.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Користувачі", description = "Операції для створення, перегляду, оновлення та видалення користувачів системи бронювання")
public class UserController {

    private final UserService userService;
    private final EntityMapper mapper;

    public UserController(UserService userService, EntityMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Створити користувача",
            description = "Створює нового користувача. Обов'язково передаються ім'я, прізвище, email і телефон. Email має бути валідним та унікальним."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Користувача створено",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні або невалідні дані користувача",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Користувач із таким email вже існує",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toUserResponse(userService.createUser(dto)));
    }

    @Operation(
            summary = "Отримати користувачів",
            description = "Повертає повний список користувачів, зареєстрованих у системі бронювання спортивних майданчиків."
    )
    @ApiResponse(responseCode = "200", description = "Список користувачів отримано",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class))))
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers().stream().map(mapper::toUserResponse).toList();
    }

    @Operation(
            summary = "Отримати користувача",
            description = "Повертає дані одного користувача за його ідентифікатором."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Користувача знайдено",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Користувача з таким id не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@Parameter(description = "Ідентифікатор користувача", example = "1") @PathVariable Long id) {
        return mapper.toUserResponse(userService.getUserById(id));
    }

    @Operation(
            summary = "Оновити користувача",
            description = "Оновлює дані користувача за id. Перевіряються обов'язкові поля, формат email та обмеження довжини значень."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Дані користувача оновлено",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні або невалідні дані користувача",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Користувача з таким id не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Email вже використовується іншим користувачем",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{id}")
    public UserResponseDto updateUser(@Parameter(description = "Ідентифікатор користувача", example = "1") @PathVariable Long id,
                                      @Valid @RequestBody UserRequestDto dto) {
        return mapper.toUserResponse(userService.updateUser(id, dto));
    }

    @Operation(
            summary = "Видалити користувача",
            description = "Видаляє користувача за ідентифікатором. Якщо користувача не існує, повертається помилка 404."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Користувача видалено"),
            @ApiResponse(responseCode = "404", description = "Користувача з таким id не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@Parameter(description = "Ідентифікатор користувача", example = "1") @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Отримати бронювання користувача",
            description = "Повертає історію бронювань конкретного користувача."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Бронювання користувача отримано",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BookingResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "Користувача з таким id не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}/bookings")
    public List<BookingResponseDto> getUserBookings(@Parameter(description = "Ідентифікатор користувача", example = "1") @PathVariable Long id) {
        return userService.getUserBookings(id).stream().map(mapper::toBookingResponse).toList();
    }
}
