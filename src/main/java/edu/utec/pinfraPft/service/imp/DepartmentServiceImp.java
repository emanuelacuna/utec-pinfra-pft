package edu.utec.pinfraPft.service.imp;

import edu.utec.pinfraPft.dto.DepartmentDto;
import edu.utec.pinfraPft.model.Department;
import edu.utec.pinfraPft.repository.DepartmentRepository;
import edu.utec.pinfraPft.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImp implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private DepartmentDto mapToDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }

    private Department mapToEntity(DepartmentDto departmentDto) {
        return Department.builder()
                .id(departmentDto.getId())
                .name(departmentDto.getName())
                .build();
    }

    @Override
    public DepartmentDto save(DepartmentDto departmentDto) {
        Department department = departmentRepository.save(mapToEntity(departmentDto));
        return mapToDto(department);
    }

    @Override
    public DepartmentDto update(Long id, DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        department.setName(departmentDto.getName());
        return mapToDto(departmentRepository.save(department));
    }

    @Override
    public void delete(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        departmentRepository.delete(department);
    }

    @Override
    public DepartmentDto findById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return mapToDto(department);
    }

    @Override
    public List<DepartmentDto> findAll() {
        return departmentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }
}
