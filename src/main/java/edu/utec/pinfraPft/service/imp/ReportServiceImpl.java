package edu.utec.pinfraPft.service.imp;

import edu.utec.pinfraPft.dto.ConstancyDto;
import edu.utec.pinfraPft.dto.EventDto;
import edu.utec.pinfraPft.dto.JustificationDto;
import edu.utec.pinfraPft.model.Constancy;
import edu.utec.pinfraPft.model.Event;
import edu.utec.pinfraPft.model.Justification;
import edu.utec.pinfraPft.model.UserEntity;
import edu.utec.pinfraPft.repository.*;
import edu.utec.pinfraPft.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final EventRepository eventRepository;
    private final ConstancyRepository constancyRepository;
    private final JustificationRepository justificationRepository;
    private final AttendanceRepository attendanceRepository;

    private List<JustificationDto> mapListJustificationToDto(List<Justification> justifications){
        List<JustificationDto> justificationDtos = new ArrayList<>();
        for(Justification justification : justifications){
            JustificationDto justificationDto = new JustificationDto();
            justificationDto.setId(justification.getId());
            justificationDto.setDate(justification.getDate());
            justificationDto.setStatus(justification.getStatus());
            justificationDto.setEvent(justification.getEvent());
            justificationDto.setInformation(justification.getInformation());
            justificationDto.setStudent(justification.getStudent().getId());
            justificationDtos.add(justificationDto);
        }
        return justificationDtos;
    }

    private List<ConstancyDto> mapListConstanciesToDto(List<Constancy> constancies){
        List<ConstancyDto> constancyDtos = new ArrayList<>();
        for(Constancy constancy : constancies){
            ConstancyDto constancyDto = new ConstancyDto();
            constancyDto.setId(constancy.getId());
            constancyDto.setDate(constancy.getDate());
            constancyDto.setStatus(constancy.getStatus());
            constancyDto.setEvent(constancy.getEvent().getId());
            constancyDto.setEventTitle(constancy.getEvent().getTitle());
            constancyDto.setConstancyType(constancy.getConstancyType());
            constancyDto.setInfo(constancy.getInfo());
            constancyDto.setStudent(constancy.getStudent().getId());
            constancyDtos.add(constancyDto);
        }
        return constancyDtos;
    }

    private List<EventDto> mapListEventsToDto(List<Event> events){
        List<EventDto> eventDtos = new ArrayList<>();
        for(Event event : events){
            EventDto eventDto = new EventDto();
            eventDto.setId(event.getId());
            eventDto.setEventType(event.getEventType());
            eventDto.setLocation(event.getLocation());
            eventDto.setItr(event.getItr().getId());
            eventDto.setItrName(event.getItr().getName());
            eventDto.setStatus(event.getStatus());
            eventDto.setMode(event.getMode());
            eventDto.setTitle(event.getTitle());
            eventDto.setStartingDate(event.getStartingDate());
            eventDto.setEndingDate(event.getEndingDate());
            eventDto.setTeachers(event.getTeachers().stream().map(UserEntity::getId).collect(Collectors.toList()));
            eventDto.setTeacherNames(event.getTeachers()
                    .stream()
                    .map(teacher -> teacher.getFirstName() + " " + teacher.getFirstSurname())
                    .collect(Collectors.toList()).toString());
            eventDtos.add(eventDto);
        }
        return eventDtos;
    }

    @Override
    public List<JustificationDto> findJustificationsByStudentId(Long studentId) {
        return mapListJustificationToDto(justificationRepository.findAllByStudent_Id(studentId));
    }

    @Override
    public List<ConstancyDto> findConstanciesByStudentId(Long studentId) {
        return mapListConstanciesToDto(constancyRepository.findAllByStudent_Id(studentId));

    }

    @Override
    public List<EventDto> findEventsByStudentId(Long userId) {
        return mapListEventsToDto(attendanceRepository.findEventsByStudentId(userId));
    }

}
