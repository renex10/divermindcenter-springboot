package divermindcenterterapia.divermindcenter.service;

import divermindcenterterapia.divermindcenter.dto.RegionDTO;
import java.util.List;

/**
 * Interfaz para los servicios relacionados con las regiones.
 */
public interface RegionService {

    /**
     * Obtiene una lista de todas las regiones con su país asociado.
     * @return lista de regiones en formato DTO.
     */
    List<RegionDTO> getAllRegions();

    /**
     * Crea una nueva región con su país asociado a partir de un DTO.
     * @param regionDTO Objeto con los datos de la región a crear.
     * @return la región creada en formato DTO.
     */
    RegionDTO createRegion(RegionDTO regionDTO);
}