package ua.opnu.labwork2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.opnu.labwork2.entity.SportCenter;

import java.util.List;

public interface SportCenterRepository extends JpaRepository<SportCenter, Long> {

    List<SportCenter> findByNameContainingIgnoreCaseOrCityContainingIgnoreCaseOrAddressContainingIgnoreCase(
            String name,
            String city,
            String address
    );

    @Query("select center.name, count(booking.id) " +
            "from SportCenter center left join center.bookings booking " +
            "group by center.id, center.name")
    List<Object[]> countBookingsByCenter();
}
