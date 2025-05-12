package divermindcenterterapia.divermindcenter.service;
//ruta del archivo service\DniService
import divermindcenterterapia.divermindcenter.dto.DniDTO;
import divermindcenterterapia.divermindcenter.entity.Dni;

import java.util.List;


import divermindcenterterapia.divermindcenter.dto.DniDTO;
import java.util.List;

public interface DniService {
    DniDTO createDni(DniDTO dniDto);
    DniDTO getDniById(Long id);
    List<DniDTO> getAllDnis();
    DniDTO updateDni(DniDTO dniDto, Long id);
    void deleteDni(Long id);
}