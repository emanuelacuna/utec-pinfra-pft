package edu.utec.pinfraPft.service.imp;

import edu.utec.pinfraPft.dto.ClaimDto;
import edu.utec.pinfraPft.dto.EventDto;
import edu.utec.pinfraPft.model.Claim;
import edu.utec.pinfraPft.model.Event;
import edu.utec.pinfraPft.model.UserEntity;
import edu.utec.pinfraPft.repository.*;
import edu.utec.pinfraPft.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImp implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ItrRepository itrRepository;
    private final EventTeacherRepository eventTeacherRepository;

    private Event mapToEntity(EventDto eventDto) {
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setTitle(eventDto.getTitle());
        event.setEventType(eventDto.getEventType());
        event.setStartingDate(eventDto.getStartingDate());
        event.setEndingDate(eventDto.getEndingDate());
        event.setMode(eventDto.getMode());
        event.setItr(itrRepository.getReferenceById(eventDto.getItr())); // Asumiendo que el objeto Itr ya está en formato adecuado
        event.setLocation(eventDto.getLocation());
        List<UserEntity> validTeachers = eventDto.getTeachers().stream()
                .map(teacherId -> userRepository.findUserById(teacherId).orElse(null))
                .filter(Objects::nonNull) // Filtrar solo aquellos que existen en el repositorio
                .collect(Collectors.toList());
        event.setTeachers(validTeachers);// Asumiendo que la lista de Teacher ya está mapeada
        event.setStatus(eventDto.getStatus());
        return event;
    }

    private EventDto mapToDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setTitle(event.getTitle());
        eventDto.setEventType(event.getEventType());
        eventDto.setStartingDate(event.getStartingDate());
        eventDto.setEndingDate(event.getEndingDate());
        eventDto.setMode(event.getMode());
        eventDto.setItr(event.getItr().getId()); // Asumiendo que el objeto Itr es adecuado para ser enviado como DTO
        eventDto.setLocation(event.getLocation());
        // Convertimos los objetos Teacher en IDs de teachers
        List<Long> teacherIds = event.getTeachers().stream()
                .map(UserEntity::getId)
                .collect(Collectors.toList());
        eventDto.setTeachers(teacherIds); // Asumiendo que la lista de Teacher es adecuada para DTO
        eventDto.setStatus(event.getStatus());
        return eventDto;
    }

    @Override
    public EventDto save(EventDto eventDto) {
        Event event = eventRepository.save(mapToEntity(eventDto));
        return mapToDto(event);
    }

    @Override
    public EventDto update(Long id, EventDto eventDto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        event.setId(eventDto.getId());
        event.setTitle(eventDto.getTitle());
        event.setEventType(eventDto.getEventType());
        event.setStartingDate(eventDto.getStartingDate());
        event.setEndingDate(eventDto.getEndingDate());
        event.setMode(eventDto.getMode());
        event.setItr(itrRepository.getReferenceById(eventDto.getItr())); // Asumiendo que el objeto Itr ya está en formato adecuado
        event.setLocation(eventDto.getLocation());
        List<UserEntity> validTeachers = eventDto.getTeachers().stream()
                .map(teacherId -> userRepository.findUserById(teacherId).orElse(null))
                .filter(Objects::nonNull) // Filtrar solo aquellos que existen en el repositorio
                .collect(Collectors.toList());
        event.setTeachers(validTeachers);// Asumiendo que la lista de Teacher ya está mapeada
        event.setStatus(eventDto.getStatus());
        return mapToDto(eventRepository.save(event));
    }

    @Override
    public void delete(Long id) {
        Event claim = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        eventRepository.delete(claim);
    }

    @Override
    public EventDto findById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return mapToDto(event);
    }

    @Override
    public List<EventDto> findAll() {
        return eventRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<EventDto> findAllByUserId(Long userId) {
        return eventTeacherRepository.findByTeachers_Id(userId).stream()
                .map(this::mapToDto)
                .toList();
    }

}
