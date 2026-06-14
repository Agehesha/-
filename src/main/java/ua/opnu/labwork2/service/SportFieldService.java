package ua.opnu.labwork2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.opnu.labwork2.dto.SportFieldRequestDto;
import ua.opnu.labwork2.entity.FieldType;
import ua.opnu.labwork2.entity.SportCenter;
import ua.opnu.labwork2.entity.SportField;
import ua.opnu.labwork2.exception.ResourceNotFoundException;
import ua.opnu.labwork2.repository.SportFieldRepository;

import java.util.List;

@Service
@Transactional
public class SportFieldService {

    private final SportFieldRepository sportFieldRepository;
    private final SportCenterService sportCenterService;
    private final FieldTypeService fieldTypeService;

    public SportFieldService(SportFieldRepository sportFieldRepository,
                             SportCenterService sportCenterService,
                             FieldTypeService fieldTypeService) {
        this.sportFieldRepository = sportFieldRepository;
        this.sportCenterService = sportCenterService;
        this.fieldTypeService = fieldTypeService;
    }

    public SportField createField(SportFieldRequestDto dto) {
        SportField field = new SportField();
        fillField(field, dto);
        return sportFieldRepository.save(field);
    }

    @Transactional(readOnly = true)
    public List<SportField> getAllFields() {
        return sportFieldRepository.findAll();
    }

    @Transactional(readOnly = true)
    public SportField getFieldById(Long id) {
        return findFieldOrThrow(id);
    }

    public SportField updateField(Long id, SportFieldRequestDto dto) {
        SportField field = findFieldOrThrow(id);
        fillField(field, dto);
        return sportFieldRepository.save(field);
    }

    public void deleteField(Long id) {
        SportField field = findFieldOrThrow(id);
        field.getFieldTypes().clear();
        sportFieldRepository.delete(field);
    }

    @Transactional(readOnly = true)
    public List<SportField> getFieldsByCenterId(Long centerId) {
        sportCenterService.findCenterOrThrow(centerId);
        return sportFieldRepository.findBySportCenterId(centerId);
    }

    public SportField addTypeToField(Long fieldId, Long typeId) {
        SportField field = findFieldOrThrow(fieldId);
        FieldType type = fieldTypeService.findFieldTypeOrThrow(typeId);
        field.getFieldTypes().add(type);
        return sportFieldRepository.save(field);
    }

    public SportField removeTypeFromField(Long fieldId, Long typeId) {
        SportField field = findFieldOrThrow(fieldId);
        FieldType type = fieldTypeService.findFieldTypeOrThrow(typeId);
        field.getFieldTypes().remove(type);
        return sportFieldRepository.save(field);
    }

    @Transactional(readOnly = true)
    public List<SportField> searchFields(String query) {
        return sportFieldRepository.findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(query, query);
    }

    public SportField findFieldOrThrow(Long id) {
        return sportFieldRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sport field not found with id: " + id));
    }

    private void fillField(SportField field, SportFieldRequestDto dto) {
        SportCenter center = sportCenterService.findCenterOrThrow(dto.sportCenterId());
        field.setName(dto.name());
        field.setLocation(dto.location());
        field.setCapacity(dto.capacity());
        field.setSportCenter(center);
    }
}
