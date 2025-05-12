package divermindcenterterapia.divermindcenter.service.impl;

import divermindcenterterapia.divermindcenter.dto.CountryDTO;
import divermindcenterterapia.divermindcenter.entity.Country;
import divermindcenterterapia.divermindcenter.repository.CountryRepository;
import divermindcenterterapia.divermindcenter.service.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<CountryDTO> getAllCountries() {
        List<Country> countries = countryRepository.findAll();
        return countries.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CountryDTO createCountry(CountryDTO countryDTO) {
        // Implementación para crear un país
        Country country = new Country();
        country.setName(countryDTO.getName());
        country.setPhoneCode(countryDTO.getPhoneCode());

        Country savedCountry = countryRepository.save(country);
        return mapToDTO(savedCountry);
    }

    private CountryDTO mapToDTO(Country country) {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setId(country.getId());
        countryDTO.setName(country.getName());
        countryDTO.setPhoneCode(country.getPhoneCode());
        return countryDTO;
    }
}