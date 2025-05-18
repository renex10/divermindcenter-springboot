package divermindcenterterapia.divermindcenter.service;

import divermindcenterterapia.divermindcenter.dto.ParentPageResponseDTO;
import divermindcenterterapia.divermindcenter.dto.ParentRegistrationDTO;
import divermindcenterterapia.divermindcenter.dto.ParentResponseDTO;
import divermindcenterterapia.divermindcenter.exception.*;

public interface ParentService {
    ParentPageResponseDTO getPagedParents(int pageNumber, int pageSize);
    ParentResponseDTO registerParent(ParentRegistrationDTO registrationDTO)
            throws DuplicateRutException, DuplicateEmailException, ResourceNotFoundException;
}