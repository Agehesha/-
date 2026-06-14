package ua.opnu.labwork2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.opnu.labwork2.dto.BookingRequestDto;
import ua.opnu.labwork2.entity.Booking;
import ua.opnu.labwork2.entity.SportCenter;
import ua.opnu.labwork2.entity.SportField;
import ua.opnu.labwork2.entity.User;
import ua.opnu.labwork2.enums.BookingStatus;
import ua.opnu.labwork2.exception.BadRequestException;
import ua.opnu.labwork2.exception.ResourceNotFoundException;
import ua.opnu.labwork2.repository.BookingRepository;

import java.util.List;

@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final SportFieldService sportFieldService;
    private final SportCenterService sportCenterService;

    public BookingService(BookingRepository bookingRepository,
                          UserService userService,
                          SportFieldService sportFieldService,
                          SportCenterService sportCenterService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.sportFieldService = sportFieldService;
        this.sportCenterService = sportCenterService;
    }

    public Booking createBooking(BookingRequestDto dto) {
        Booking booking = new Booking();
        fillBooking(booking, dto);
        if (dto.status() == null) {
            booking.setStatus(BookingStatus.CREATED);
        }
        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Booking getBookingById(Long id) {
        return findBookingOrThrow(id);
    }

    public Booking updateBooking(Long id, BookingRequestDto dto) {
        Booking booking = findBookingOrThrow(id);
        fillBooking(booking, dto);
        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(Long id) {
        Booking booking = findBookingOrThrow(id);
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public List<Booking> getActiveBookings() {
        return bookingRepository.findByStatusIn(List.of(BookingStatus.CREATED, BookingStatus.CONFIRMED));
    }

    public Booking findBookingOrThrow(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    private void fillBooking(Booking booking, BookingRequestDto dto) {
        if (!dto.endTime().isAfter(dto.startTime())) {
            throw new BadRequestException("End time must be after start time");
        }

        User user = userService.findUserOrThrow(dto.userId());
        SportField field = sportFieldService.findFieldOrThrow(dto.sportFieldId());

        SportCenter center;
        if (dto.sportCenterId() != null) {
            center = sportCenterService.findCenterOrThrow(dto.sportCenterId());
        } else {
            center = field.getSportCenter();
        }

        if (center == null) {
            throw new BadRequestException("Booking must be linked to a sport center");
        }

        booking.setStartTime(dto.startTime());
        booking.setEndTime(dto.endTime());
        booking.setStatus(dto.status() == null ? BookingStatus.CREATED : dto.status());
        booking.setUser(user);
        booking.setSportField(field);
        booking.setSportCenter(center);
    }
}
