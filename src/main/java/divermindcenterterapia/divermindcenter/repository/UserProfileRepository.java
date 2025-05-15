// UserProfileRepository.java
package divermindcenterterapia.divermindcenter.repository;

import divermindcenterterapia.divermindcenter.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}