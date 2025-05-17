package divermindcenterterapia.divermindcenter.repository;

import divermindcenterterapia.divermindcenter.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REPOSITORIO DE PADRES - "EL ARCHIVERO DIGITAL"
 * ----------------------------------------------
 * ¿Qué hace este archivo?
 * - Es el encargado de guardar, buscar y organizar a los padres en la base de datos.
 * - Funciona como un asistente automático para operaciones comunes.
 *
 * Métodos principales (Spring los implementa solo con el nombre):
 * 1. Buscar por email
 * 2. Verificar si un email existe
 * 3. Buscar padres activos/inactivos
 * 4. Buscar por nombre/apellido
 */
public interface ParentRepository extends JpaRepository<Parent, Long> {

    // 🔍 BUSCAR POR EMAIL (EXACTO)
    Optional<Parent> findByEmail(String email);

    // ✔️ VERIFICAR SI UN EMAIL YA EXISTE
    boolean existsByEmail(String email);

    // 📱 BUSCAR PADRES CON TELÉFONO DE UN PAÍS (Ej: Chile = +56)
    @Query("SELECT p FROM Parent p JOIN p.phones ph WHERE ph.country.id = :countryId")
    List<Parent> findByPhoneCountry(@Param("countryId") Long countryId);

    // 🎯 BUSCAR PADRES ACTIVOS/INACTIVOS
    List<Parent> findByIsActive(Boolean isActive);

    // 👨‍👩‍👧‍👦 BUSCAR POR NOMBRE O APELLIDO (BÚSQUEDA FLEXIBLE)
    @Query("SELECT p FROM Parent p WHERE LOWER(p.firstName) LIKE LOWER(concat('%', :name, '%')) OR LOWER(p.lastName) LIKE LOWER(concat('%', :name, '%'))")
    List<Parent> searchByName(@Param("name") String name);

    // 📅 BUSCAR PADRES REGISTRADOS EN UN RANGO DE FECHAS
    @Query("SELECT p FROM Parent p WHERE p.createdAt BETWEEN :startDate AND :endDate")
    List<Parent> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}