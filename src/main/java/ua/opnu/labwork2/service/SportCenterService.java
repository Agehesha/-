package ua.opnu.labwork2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.opnu.labwork2.dto.SportCenterRequestDto;
import ua.opnu.labwork2.entity.SportCenter;
import ua.opnu.labwork2.entity.SportField;
import ua.opnu.labwork2.exception.ResourceNotFoundException;
import ua.opnu.labwork2.repository.SportCenterRepository;
import ua.opnu.labwork2.repository.SportFieldRepository;

import java.util.List;

@Service
@Transactional
public class SportCenterService {

    private final SportCenterRepository sportCenterRepository;
    private final SportFieldRepository sportFieldRepository;

    public SportCenterService(SportCenterRepository sportCenterRepository,
                              SportFieldRepository sportFieldRepository) {
        this.sportCenterRepository = sportCenterRepository;
        this.sportFieldRepository = sportFieldRepository;
    }

    public SportCenter createCenter(SportCenterRequestDto dto) {
        SportCenter center = new SportCenter();
        fillCenter(center, dto);
        return sportCenterRepository.save(center);
    }

    @Transactional(readOnly = true)
    public List<SportCenter> getAllCenters() {
        return sportCenterRepository.findAll();
    }

    @Transactional(readOnly = true)
    public SportCenter getCenterById(Long id) {
        return findCenterOrThrow(id);
    }

    public SportCenter updateCenter(Long id, SportCenterRequestDto dto) {
        SportCenter center = findCenterOrThrow(id);
        fillCenter(center, dto);
        return sportCenterRepository.save(center);
    }

    public void deleteCenter(Long id) {
        SportCenter center = findCenterOrThrow(id);
        sportCenterRepository.delete(center);
    }

    @Transactional(readOnly = true)
    public List<SportField> getFieldsByCenterId(Long centerId) {
        findCenterOrThrow(centerId);
        return sportFieldRepository.findBySportCenterId(centerId);
    }

    @Transactional(readOnly = true)
    public List<SportCenter> searchCenters(String query) {
        return sportCenterRepository.findByNameContainingIgnoreCaseOrCityContainingIgnoreCaseOrAddressContainingIgnoreCase(
                query, query, query
        );
    }

    public SportCenter findCenterOrThrow(Long id) {
        return sportCenterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sport center not found with id: " + id));
    }

    private void fillCenter(SportCenter center, SportCenterRequestDto dto) {
        center.setName(dto.name());
        center.setCity(dto.city());
        center.setAddress(dto.address());
    }
}
