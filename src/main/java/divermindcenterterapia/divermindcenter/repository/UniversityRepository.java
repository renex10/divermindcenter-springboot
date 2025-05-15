// UniversityRepository.java
package divermindcenterterapia.divermindcenter.repository;

import divermindcenterterapia.divermindcenter.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Long> {
    boolean existsByName(String name);
}