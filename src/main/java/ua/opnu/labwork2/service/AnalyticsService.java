package ua.opnu.labwork2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.opnu.labwork2.enums.BookingStatus;
import ua.opnu.labwork2.repository.BookingRepository;
import ua.opnu.labwork2.repository.FieldTypeRepository;
import ua.opnu.labwork2.repository.SportCenterRepository;
import ua.opnu.labwork2.repository.SportFieldRepository;
import ua.opnu.labwork2.repository.UserRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class AnalyticsService {

    private final SportFieldRepository sportFieldRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final FieldTypeRepository fieldTypeRepository;
    private final SportCenterRepository sportCenterRepository;

    public AnalyticsService(SportFieldRepository sportFieldRepository,
                            UserRepository userRepository,
                            BookingRepository bookingRepository,
                            FieldTypeRepository fieldTypeRepository,
                            SportCenterRepository sportCenterRepository) {
        this.sportFieldRepository = sportFieldRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.fieldTypeRepository = fieldTypeRepository;
        this.sportCenterRepository = sportCenterRepository;
    }

    public long getFieldsCount() {
        return sportFieldRepository.count();
    }

    public long getUsersCount() {
        return userRepository.count();
    }

    public long getActiveBookingsCount() {
        return bookingRepository.countByStatusIn(List.of(BookingStatus.CREATED, BookingStatus.CONFIRMED));
    }

    public Map<String, Long> getFieldsByType() {
        return toMap(fieldTypeRepository.countFieldsByType());
    }

    public Map<String, Long> getCentersWorkload() {
        return toMap(sportCenterRepository.countBookingsByCenter());
    }

    private Map<String, Long> toMap(List<Object[]> rows) {
        Map<String, Long> result = new LinkedHashMap<>();
        for (Object[] row : rows) {
            String name = (String) row[0];
            Long count = ((Number) row[1]).longValue();
            result.put(name, count);
        }
        return result;
    }
}
