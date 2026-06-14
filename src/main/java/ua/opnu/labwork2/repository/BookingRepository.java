package ua.opnu.labwork2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.labwork2.entity.Booking;
import ua.opnu.labwork2.enums.BookingStatus;

import java.util.Collection;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    List<Booking> findByStatusIn(Collection<BookingStatus> statuses);

    long countByStatusIn(Collection<BookingStatus> statuses);
}
