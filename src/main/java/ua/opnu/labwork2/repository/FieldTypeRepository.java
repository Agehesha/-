package ua.opnu.labwork2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.opnu.labwork2.entity.FieldType;

import java.util.List;

public interface FieldTypeRepository extends JpaRepository<FieldType, Long> {

    @Query("select type.name, count(field.id) " +
            "from FieldType type left join type.sportFields field " +
            "group by type.id, type.name")
    List<Object[]> countFieldsByType();
}
