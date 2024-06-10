package edu.utec.pinfraPft.service.imp;

import edu.utec.pinfraPft.dto.ItrDto;
import edu.utec.pinfraPft.model.Itr;
import edu.utec.pinfraPft.repository.ItrRepository;
import edu.utec.pinfraPft.service.ItrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItrServiceImp implements ItrService {

    private final ItrRepository itrRepository;

    private ItrDto mapToDto(Itr itr) {
        return ItrDto.builder()
                .id(itr.getId())
                .name(itr.getName())
                .build();
    }

    private Itr mapToEntity(ItrDto itrDto) {
        return Itr.builder()
                .id(itrDto.getId())
                .name(itrDto.getName())
                .build();
    }

    @Override
    public ItrDto save(ItrDto itrDto) {
        Itr itr = itrRepository.save(mapToEntity(itrDto));
        return mapToDto(itr);
    }

    @Override
    public ItrDto update(Long id, ItrDto itrDto) {
        Itr itr = itrRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itr not found"));
        itr.setName(itrDto.getName());
        return mapToDto(itrRepository.save(itr));
    }

    @Override
    public void delete(Long id) {
        Itr itr = itrRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itr not found"));
        itrRepository.delete(itr);
    }

    @Override
    public ItrDto findById(Long id) {
        Itr itr = itrRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itr not found"));
        return mapToDto(itr);
    }

    @Override
    public List<ItrDto> findAll() {
        return itrRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }
}
