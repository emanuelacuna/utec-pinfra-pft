package edu.utec.pinfraPft.service.imp;

import edu.utec.pinfraPft.dto.ConstancyDto;
import edu.utec.pinfraPft.model.Constancy;
import edu.utec.pinfraPft.model.Event;
import edu.utec.pinfraPft.model.UserEntity;
import edu.utec.pinfraPft.repository.ConstancyRepository;
import edu.utec.pinfraPft.repository.EventRepository;
import edu.utec.pinfraPft.repository.UserRepository;
import edu.utec.pinfraPft.service.ConstancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConstancyServiceImpl implements ConstancyService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ConstancyRepository constancyRepository;


    private Constancy mapToEntity(ConstancyDto constancyDto) {
        Constancy constancy = new Constancy();
        constancy.setId(constancyDto.getId());
        constancy.setConstancyType(constancyDto.getConstancyType());
        constancy.setInfo(constancyDto.getInfo());
        constancy.setEvent(eventRepository.findById(constancyDto.getEvent())
                .orElseThrow(() -> new RuntimeException("Event not found")));
        constancy.setStudent(userRepository.findById(constancyDto.getStudent())
                .orElseThrow(() -> new RuntimeException("Student not found")));
        constancy.setDate(constancyDto.getDate());
        constancy.setStatus(constancyDto.getStatus());

        return constancy;
    }

    private ConstancyDto mapToDto(Constancy constancy) {
        ConstancyDto constancyDto = new ConstancyDto();
        constancyDto.setId(constancy.getId());
        constancyDto.setConstancyType(constancy.getConstancyType());
        constancyDto.setInfo(constancy.getInfo());
        constancyDto.setEvent(constancy.getEvent().getId());
        constancyDto.setStudent(constancy.getStudent().getId());
        constancyDto.setDate(constancy.getDate());
        constancyDto.setStatus(constancy.getStatus());

        return constancyDto;
    }


    @Override
    public ConstancyDto save(ConstancyDto constancyDto) {
        Constancy constancy = constancyRepository.save(mapToEntity(constancyDto));
        return mapToDto(constancy);
    }

    @Override
    public ConstancyDto update(Long id, ConstancyDto constancyDto) {
        Constancy constancy = constancyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Constancy not found"));
        constancy.setId(constancyDto.getId());
        constancy.setConstancyType(constancyDto.getConstancyType());
        constancy.setDate(constancyDto.getDate());
        constancy.setInfo(constancyDto.getInfo());
        constancy.setEvent(eventRepository.findById(constancyDto.getEvent())
                .orElseThrow(() -> new RuntimeException("Event not found")));

        return mapToDto(constancyRepository.save(constancy));
    }

    @Override
    public void delete(Long id) {
        Constancy constancy = constancyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Constancy not found"));
        constancyRepository.delete(constancy);
    }

    @Override
    public ConstancyDto findById(Long id) {
        Constancy constancy = constancyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Constancy not found"));
        return mapToDto(constancy);
    }

    @Override
    public List<ConstancyDto> findAll() {
        return constancyRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<ConstancyDto> findConstancyByStudent_Id(Long userId) {
        return constancyRepository.findConstancyByStudent_Id(userId).stream()
                .map(this::mapToDto)
                .toList();
    }
}
