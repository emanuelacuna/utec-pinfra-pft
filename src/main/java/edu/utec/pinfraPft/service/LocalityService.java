package edu.utec.pinfraPft.service;

import edu.utec.pinfraPft.dto.LocalityDto;

import java.util.List;

public interface LocalityService {

    LocalityDto save(LocalityDto localityDto);

    LocalityDto update(Long id, LocalityDto localityDto);

    void delete(Long id);

    LocalityDto findById(Long id);

    List<LocalityDto> findAll();
}
