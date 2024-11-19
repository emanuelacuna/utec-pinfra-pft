package edu.utec.pinfraPft.service;

import edu.utec.pinfraPft.dto.ConstancyDto;
import edu.utec.pinfraPft.dto.EventDto;
import edu.utec.pinfraPft.dto.JustificationDto;

import java.util.List;

public interface ReportService {
    List<JustificationDto> findJustificationsByStudentId(Long studentId);
    List<ConstancyDto> findConstanciesByStudentId(Long studentId);
    List<EventDto> findEventsByStudentId(Long studentId);
}
