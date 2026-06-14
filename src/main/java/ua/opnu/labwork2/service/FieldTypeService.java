package ua.opnu.labwork2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.opnu.labwork2.dto.FieldTypeRequestDto;
import ua.opnu.labwork2.entity.FieldType;
import ua.opnu.labwork2.entity.SportField;
import ua.opnu.labwork2.exception.ResourceNotFoundException;
import ua.opnu.labwork2.repository.FieldTypeRepository;
import ua.opnu.labwork2.repository.SportFieldRepository;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class FieldTypeService {

    private final FieldTypeRepository fieldTypeRepository;
    private final SportFieldRepository sportFieldRepository;

    public FieldTypeService(FieldTypeRepository fieldTypeRepository,
                            SportFieldRepository sportFieldRepository) {
        this.fieldTypeRepository = fieldTypeRepository;
        this.sportFieldRepository = sportFieldRepository;
    }

    public FieldType createFieldType(FieldTypeRequestDto dto) {
        FieldType type = new FieldType();
        type.setName(dto.name());
        type.setDescription(dto.description());
        return fieldTypeRepository.save(type);
    }

    @Transactional(readOnly = true)
    public List<FieldType> getAllFieldTypes() {
        return fieldTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public FieldType getFieldTypeById(Long id) {
        return findFieldTypeOrThrow(id);
    }

    public void deleteFieldType(Long id) {
        FieldType type = findFieldTypeOrThrow(id);
        for (SportField field : new HashSet<>(type.getSportFields())) {
            field.getFieldTypes().remove(type);
            sportFieldRepository.save(field);
        }
        fieldTypeRepository.delete(type);
    }

    public FieldType findFieldTypeOrThrow(Long id) {
        return fieldTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Field type not found with id: " + id));
    }
}
