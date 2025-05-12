package divermindcenterterapia.divermindcenter.service.impl;

import divermindcenterterapia.divermindcenter.dto.RegionDTO;
import divermindcenterterapia.divermindcenter.entity.Region;
import divermindcenterterapia.divermindcenter.repository.RegionRepository;
import divermindcenterterapia.divermindcenter.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación concreta del servicio de gestión de regiones.
 *
 * Responsabilidades:
 * - Gestionar operaciones CRUD para regiones
 * - Convertir entre entidades y DTOs
 * - Manejar transacciones de base de datos
 */
@Service // Marca esta clase como un componente de servicio de Spring
@RequiredArgsConstructor // Genera automáticamente el constructor con dependencias requeridas
@Transactional // Todas las operaciones públicas son transaccionales por defecto
public class RegionServiceImpl implements RegionService {

    // Inyección del repositorio para operaciones de base de datos
    private final RegionRepository regionRepository;

    /**
     * Obtiene todas las regiones disponibles.
     *
     * Flujo de ejecución:
     * 1. Invoca al repositorio para obtener todas las entidades Region
     * 2. Convierte cada entidad a su DTO correspondiente
     * 3. Retorna la lista de DTOs
     *
     * @return Lista de RegionDTO con todas las regiones
     */
    @Override
    @Transactional(readOnly = true) // Optimización para operaciones de solo lectura
    public List<RegionDTO> getAllRegions() {
        return regionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crea una nueva región a partir de los datos proporcionados.
     *
     * Flujo de ejecución:
     * 1. Crea una nueva entidad Region
     * 2. Establece los valores básicos (nombre)
     * 3. Guarda la entidad en base de datos
     * 4. Convierte la entidad guardada a DTO
     * 5. Retorna el DTO creado
     *
     * @param regionDTO Datos para la creación de la región
     * @return RegionDTO con los datos de la región creada
     */
    @Override
    public RegionDTO createRegion(RegionDTO regionDTO) {
        Region region = new Region();
        region.setName(regionDTO.getName());

        // Sección para futura implementación de relación con país
        // if (regionDTO.getCountryId() != null) {
        //     Country country = countryService.getCountryById(regionDTO.getCountryId());
        //     region.setCountry(country);
        // }

        return convertToDTO(regionRepository.save(region));
    }

    /**
     * Convierte una entidad Region a su correspondiente DTO.
     *
     * Proceso de conversión:
     * 1. Crea un nuevo DTO
     * 2. Copia los campos básicos (id, nombre)
     * 3. Si existe relación con país, copia sus datos
     *
     * @param region Entidad a convertir
     * @return DTO con los datos de la región
     */
    private RegionDTO convertToDTO(Region region) {
        RegionDTO dto = new RegionDTO();
        dto.setId(region.getId());
        dto.setName(region.getName());

        // Manejo de la relación opcional con país
        if (region.getCountry() != null) {
            dto.setCountryId(region.getCountry().getId());
            dto.setCountryName(region.getCountry().getName());
            dto.setPhoneCode(region.getCountry().getPhoneCode());
        }

        return dto;
    }
}