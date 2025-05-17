package divermindcenterterapia.divermindcenter.service;

import divermindcenterterapia.divermindcenter.dto.ParentPageResponseDTO;

public interface ParentService {
    ParentPageResponseDTO getPagedParents(int pageNumber, int pageSize);
}