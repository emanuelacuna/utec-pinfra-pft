package edu.utec.pinfraPft.service.imp;

import edu.utec.pinfraPft.dto.ClaimDto;
import edu.utec.pinfraPft.model.Claim;
import edu.utec.pinfraPft.repository.ClaimRepository;
import edu.utec.pinfraPft.service.ClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClaimServiceImp implements ClaimService {

    private final ClaimRepository claimRepository;

    private ClaimDto mapToDto(Claim claim) {
        ClaimDto claimDto = new ClaimDto();
        claimDto.setId(claim.getId());
        claimDto.setTitle(claim.getTitle());
        claimDto.setDescription(claim.getDescription());
        claimDto.setCreated(claim.getCreated());
        claimDto.setUpdated(claim.getUpdated());
        claimDto.setEventVme(claim.getEventVme());
        claimDto.setActivityApe(claim.getActivityApe());
        claimDto.setSemester(claim.getSemester());
        claimDto.setDate(claim.getDate());
        claimDto.setTeacher(claim.getTeacher());
        claimDto.setCredits(claim.getCredits());
        claimDto.setStatus(claim.getStatus());
        return claimDto;
    }

    private Claim mapToEntity(ClaimDto claimDto) {
        Claim claim = new Claim();
        claim.setId(claimDto.getId());
        claim.setTitle(claimDto.getTitle());
        claim.setDescription(claimDto.getDescription());
        claim.setCreated(claimDto.getCreated());
        claim.setUpdated(claimDto.getUpdated());
        claim.setEventVme(claimDto.getEventVme());
        claim.setActivityApe(claimDto.getActivityApe());
        claim.setSemester(claimDto.getSemester());
        claim.setDate(claimDto.getDate());
        claim.setTeacher(claimDto.getTeacher());
        claim.setCredits(claimDto.getCredits());
        claim.setStatus(claimDto.getStatus());
        return claim;
    }

    @Override
    public ClaimDto save(ClaimDto claimDto) {
        claimDto.setCreated(LocalDateTime.now());
        Claim claim = claimRepository.save(mapToEntity(claimDto));
        return mapToDto(claim);
    }

    @Override
    public ClaimDto update(Long id, ClaimDto claimDto) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        claim.setTitle(claimDto.getTitle());
        claim.setDescription(claimDto.getDescription());
        claim.setUpdated(LocalDateTime.now());
        claim.setEventVme(claimDto.getEventVme());
        claim.setActivityApe(claimDto.getActivityApe());
        claim.setSemester(claimDto.getSemester());
        claim.setDate(claimDto.getDate());
        claim.setTeacher(claimDto.getTeacher());
        claim.setCredits(claimDto.getCredits());
        claim.setStatus(claimDto.getStatus());
        return mapToDto(claimRepository.save(claim));
    }

    @Override
    public void delete(Long id) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        claimRepository.delete(claim);
    }

    @Override
    public ClaimDto findById(Long id) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        return mapToDto(claim);
    }

    @Override
    public List<ClaimDto> findAll() {
        return claimRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<ClaimDto> findAllByUserId(Long id) {
        return claimRepository.findAllByUserId(id)
                .stream()
                .map(this::mapToDto)
                .toList();
    }
}
