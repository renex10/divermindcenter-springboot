package divermindcenterterapia.divermindcenter.repository;

import divermindcenterterapia.divermindcenter.entity.Dni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DniRepository extends JpaRepository<Dni, Long> {
    // Método para verificar si un RUT ya existe
    boolean existsByValue(String value);

    // Opcional: Método para buscar por el valor exacto
    Dni findByValue(String value);
}