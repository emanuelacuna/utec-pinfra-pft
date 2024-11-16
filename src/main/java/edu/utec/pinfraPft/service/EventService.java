package edu.utec.pinfraPft.service;

import edu.utec.pinfraPft.dto.ClaimDto;
import edu.utec.pinfraPft.dto.EventDto;

import java.util.List;

public interface EventService {

    EventDto save(EventDto eventDto);

    EventDto update(Long id, EventDto eventDto);

    void delete(Long id);

    EventDto findById(Long id);

    List<EventDto> findAll();

    List<EventDto> findAllByUserId(Long userId);
}
