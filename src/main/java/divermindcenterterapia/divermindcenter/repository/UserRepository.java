package divermindcenterterapia.divermindcenter.repository;

import divermindcenterterapia.divermindcenter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); // Método para verificar email único

    // Nuevos métodos para manejar el estado active
    List<User> findByActive(boolean active);

    Optional<User> findByIdAndActive(Long id, boolean active);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.active = :active")
    Optional<User> findByEmailAndActive(@Param("email") String email,
                                        @Param("active") boolean active);

    long countByActive(boolean active);
}