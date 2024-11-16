package edu.utec.pinfraPft.service.imp;

import edu.utec.pinfraPft.dto.ActionTakenDto;
import edu.utec.pinfraPft.model.ActionTaken;
import edu.utec.pinfraPft.repository.ActionTakenRepository;
import edu.utec.pinfraPft.repository.ClaimRepository;
import edu.utec.pinfraPft.repository.UserRepository;
import edu.utec.pinfraPft.service.ActionTakenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionTakenServiceImpl implements ActionTakenService {

    private final ActionTakenRepository actionTakenRepository;
    private final ClaimRepository claimRepository;
    private final UserRepository userRepository;


    private ActionTaken mapToEntity(ActionTakenDto actionTakenDto) {
        ActionTaken actionTaken = new ActionTaken();
        actionTaken.setId(actionTakenDto.getId());
        actionTaken.setAdmin(userRepository.findById(actionTakenDto.getAdmin())
                .orElseThrow(() -> new RuntimeException("User not found"))); // Asegúrate de que el Dto contenga el objeto Student
        actionTaken.setClaim(claimRepository.findClaimById(actionTakenDto.getClaim()));
        actionTaken.setStatus(actionTakenDto.getStatus());
        actionTaken.setActionTaken(actionTakenDto.getActionTaken());
        return actionTaken;
    }

    private ActionTakenDto mapToDto(ActionTaken actionTaken) {
        ActionTakenDto actionTakenDto = new ActionTakenDto();
        actionTakenDto.setId(actionTaken.getId());
        actionTakenDto.setAdmin(actionTaken.getAdmin().getId());
        actionTakenDto.setClaim(actionTaken.getClaim().getId());
        actionTakenDto.setStatus(actionTaken.getStatus());
        actionTakenDto.setActionTaken(actionTaken.getActionTaken());
        return actionTakenDto;
    }


    @Override
    public void save(ActionTakenDto actionTakenDto) {
        ActionTaken actionTaken = mapToEntity(actionTakenDto);
        actionTakenRepository.save(actionTaken);
    }
}
