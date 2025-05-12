package divermindcenterterapia.divermindcenter.controller;

import divermindcenterterapia.divermindcenter.dto.CountryDTO;
import divermindcenterterapia.divermindcenter.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gestionar países.
 */
@RestController
@RequestMapping("/api/countries")
@CrossOrigin(origins = "*")
public class CountryController {

    @Autowired
    private CountryService countryService;

    /**
     * Devuelve la lista de todos los países.
     */
    @GetMapping
    public List<CountryDTO> getAllCountries() {
        return countryService.getAllCountries();
    }

    /**
     * Crea un nuevo país.
     */
    @PostMapping
    public CountryDTO createCountry(@RequestBody CountryDTO countryDTO) {
        return countryService.createCountry(countryDTO);
    }
}