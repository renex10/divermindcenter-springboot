package divermindcenterterapia.divermindcenter.service;

import divermindcenterterapia.divermindcenter.dto.CountryDTO;
import java.util.List;

/**
 * Interfaz para los servicios relacionados con los países.
 */
public interface CountryService {

    /**
     * Obtiene una lista de todos los países.
     * @return lista de países en formato DTO.
     */
    List<CountryDTO> getAllCountries();

    /**
     * Crea un nuevo país a partir de un DTO.
     * @param countryDTO Objeto con los datos del país a crear.
     * @return el país creado en formato DTO.
     */
    CountryDTO createCountry(CountryDTO countryDTO);
}