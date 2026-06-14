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
import ua.opnu.labwork2.dto.BookingRequestDto;
import ua.opnu.labwork2.dto.BookingResponseDto;
import ua.opnu.labwork2.dto.ErrorResponseDto;
import ua.opnu.labwork2.mapper.EntityMapper;
import ua.opnu.labwork2.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@Tag(name = "Бронювання", description = "Операції для створення, перегляду, оновлення та скасування бронювань спортивних майданчиків")
public class BookingController {

    private final BookingService bookingService;
    private final EntityMapper mapper;

    public BookingController(BookingService bookingService, EntityMapper mapper) {
        this.bookingService = bookingService;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Створити бронювання",
            description = "Створює нове бронювання спортивного майданчика для користувача. Перевіряється наявність користувача, майданчика, спортивного центру та коректність часу."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Бронювання створено",
                    content = @Content(schema = @Schema(implementation = BookingResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні дані бронювання або помилка бізнес-правил",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Користувача, майданчик або спортивний центр не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@Valid @RequestBody BookingRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toBookingResponse(bookingService.createBooking(dto)));
    }

    @Operation(
            summary = "Отримати бронювання",
            description = "Повертає список усіх бронювань у системі."
    )
    @ApiResponse(responseCode = "200", description = "Список бронювань отримано",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BookingResponseDto.class))))
    @GetMapping
    public List<BookingResponseDto> getAllBookings() {
        return bookingService.getAllBookings().stream().map(mapper::toBookingResponse).toList();
    }

    @Operation(
            summary = "Отримати бронювання за id",
            description = "Повертає детальну інформацію про бронювання за його ідентифікатором."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Бронювання знайдено",
                    content = @Content(schema = @Schema(implementation = BookingResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Бронювання з таким id не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public BookingResponseDto getBookingById(@Parameter(description = "Ідентифікатор бронювання", example = "1") @PathVariable Long id) {
        return mapper.toBookingResponse(bookingService.getBookingById(id));
    }

    @Operation(
            summary = "Оновити бронювання",
            description = "Оновлює час, статус і зв'язки бронювання. Обов'язкові поля проходять валідацію, а пов'язані сутності повинні існувати."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Бронювання оновлено",
                    content = @Content(schema = @Schema(implementation = BookingResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні дані бронювання",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Бронювання або пов'язана сутність не знайдені",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{id}")
    public BookingResponseDto updateBooking(@Parameter(description = "Ідентифікатор бронювання", example = "1") @PathVariable Long id,
                                            @Valid @RequestBody BookingRequestDto dto) {
        return mapper.toBookingResponse(bookingService.updateBooking(id, dto));
    }

    @Operation(
            summary = "Скасувати бронювання",
            description = "Скасовує бронювання за id та повертає оновлену модель зі статусом CANCELLED."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Бронювання скасовано",
                    content = @Content(schema = @Schema(implementation = BookingResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Бронювання з таким id не знайдено",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public BookingResponseDto cancelBooking(@Parameter(description = "Ідентифікатор бронювання", example = "1") @PathVariable Long id) {
        return mapper.toBookingResponse(bookingService.cancelBooking(id));
    }

    @Operation(
            summary = "Отримати активні бронювання",
            description = "Повертає бронювання, які не завершені та не скасовані."
    )
    @ApiResponse(responseCode = "200", description = "Активні бронювання отримано",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BookingResponseDto.class))))
    @GetMapping("/active")
    public List<BookingResponseDto> getActiveBookings() {
        return bookingService.getActiveBookings().stream().map(mapper::toBookingResponse).toList();
    }
}
