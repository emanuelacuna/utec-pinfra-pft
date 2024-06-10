package edu.utec.pinfraPft.service;

import edu.utec.pinfraPft.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    DepartmentDto save(DepartmentDto departmentDto);

    DepartmentDto update(Long id, DepartmentDto departmentDto);

    void delete(Long id);

    DepartmentDto findById(Long id);

    List<DepartmentDto> findAll();
}
