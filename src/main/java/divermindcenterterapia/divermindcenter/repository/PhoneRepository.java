package divermindcenterterapia.divermindcenter.repository;

import divermindcenterterapia.divermindcenter.entity.Dni;
import divermindcenterterapia.divermindcenter.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone,Long> {
}
