package ua.opnu.labwork2.mapper;

import org.springframework.stereotype.Component;
import ua.opnu.labwork2.dto.*;
import ua.opnu.labwork2.entity.*;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EntityMapper {

    public UserResponseDto toUserResponse(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone()
        );
    }

    public SportCenterResponseDto toSportCenterResponse(SportCenter center) {
        return new SportCenterResponseDto(
                center.getId(),
                center.getName(),
                center.getCity(),
                center.getAddress()
        );
    }

    public FieldTypeResponseDto toFieldTypeResponse(FieldType type) {
        return new FieldTypeResponseDto(
                type.getId(),
                type.getName(),
                type.getDescription()
        );
    }

    public SportFieldResponseDto toSportFieldResponse(SportField field) {
        Long centerId = field.getSportCenter() == null ? null : field.getSportCenter().getId();
        String centerName = field.getSportCenter() == null ? null : field.getSportCenter().getName();
        Set<FieldTypeResponseDto> types = field.getFieldTypes().stream()
                .map(this::toFieldTypeResponse)
                .collect(Collectors.toSet());

        return new SportFieldResponseDto(
                field.getId(),
                field.getName(),
                field.getLocation(),
                field.getCapacity(),
                centerId,
                centerName,
                types
        );
    }

    public BookingResponseDto toBookingResponse(Booking booking) {
        User user = booking.getUser();
        SportField field = booking.getSportField();
        SportCenter center = booking.getSportCenter();
        String userFullName = user.getFirstName() + " " + user.getLastName();

        return new BookingResponseDto(
                booking.getId(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getStatus(),
                user.getId(),
                userFullName,
                field.getId(),
                field.getName(),
                center.getId(),
                center.getName()
        );
    }
}
