package edu.utec.pinfraPft.service.imp;

import edu.utec.pinfraPft.dto.LocalityDto;
import edu.utec.pinfraPft.model.Locality;
import edu.utec.pinfraPft.repository.LocalityRepository;
import edu.utec.pinfraPft.service.LocalityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalityServiceImp implements LocalityService {

    private final LocalityRepository localityRepository;

    private LocalityDto mapToDto(Locality locality) {
        return LocalityDto.builder()
                .id(locality.getId())
                .name(locality.getName())
                .build();
    }

    private Locality mapToEntity(LocalityDto localityDto) {
        return Locality.builder()
                .id(localityDto.getId())
                .name(localityDto.getName())
                .build();
    }

    @Override
    public LocalityDto save(LocalityDto localityDto) {
        Locality locality = localityRepository.save(mapToEntity(localityDto));
        return mapToDto(locality);
    }

    @Override
    public LocalityDto update(Long id, LocalityDto localityDto) {
        Locality locality = localityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locality not found"));
        locality.setName(localityDto.getName());
        return mapToDto(localityRepository.save(locality));
    }

    @Override
    public void delete(Long id) {
        Locality locality = localityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locality not found"));
        localityRepository.delete(locality);
    }

    @Override
    public LocalityDto findById(Long id) {
        Locality locality = localityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locality not found"));
        return mapToDto(locality);
    }

    @Override
    public List<LocalityDto> findAll() {
        return localityRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }
}
