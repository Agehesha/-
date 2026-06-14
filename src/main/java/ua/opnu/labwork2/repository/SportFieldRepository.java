package ua.opnu.labwork2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.labwork2.entity.SportField;

import java.util.List;

public interface SportFieldRepository extends JpaRepository<SportField, Long> {

    List<SportField> findBySportCenterId(Long sportCenterId);

    List<SportField> findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(String name, String location);
}
