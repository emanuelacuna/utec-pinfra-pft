package edu.utec.pinfraPft.service;

import edu.utec.pinfraPft.dto.ClaimDto;

import java.util.List;

public interface ClaimService {

    ClaimDto save(ClaimDto claimDto);

    ClaimDto update(Long id, ClaimDto claimDto);

    void delete(Long id);

    ClaimDto findById(Long id);

    List<ClaimDto> findAll();

    List<ClaimDto> findAllByUserId(Long id);

    void changeStatus(Long id, String status);

}
