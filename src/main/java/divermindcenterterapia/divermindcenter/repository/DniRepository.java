package divermindcenterterapia.divermindcenter.repository;
//ruta dela archivo es repository\DniRepository
import divermindcenterterapia.divermindcenter.entity.Dni;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DniRepository extends JpaRepository<Dni,Long> {
}
