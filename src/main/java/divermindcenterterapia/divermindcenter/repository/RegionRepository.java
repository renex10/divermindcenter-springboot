package divermindcenterterapia.divermindcenter.repository;

import divermindcenterterapia.divermindcenter.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para operaciones de base de datos con Region.
 *
 * Responsabilidades:
 * - Proporcionar operaciones CRUD básicas heredadas de JpaRepository
 * - Permitir consultas personalizadas cuando sea necesario
 *
 * Spring Data JPA implementa automáticamente esta interfaz
 */
public interface RegionRepository extends JpaRepository<Region, Long> {
    // Consultas personalizadas pueden agregarse aquí:
    // Ejemplo: List<Region> findByCountryId(Long countryId);
}