package edu.utec.pinfraPft.service;

import edu.utec.pinfraPft.dto.ConstancyDto;

import java.util.List;

public interface ConstancyService {

    ConstancyDto save(ConstancyDto constancyDto);

    ConstancyDto update(Long id, ConstancyDto constancyDto);

    void delete(Long id);

    ConstancyDto findById(Long id);

    List<ConstancyDto> findAll();

    List<ConstancyDto> findConstancyByStudent_Id(Long userId);


}
