package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Entity representing a Therapist's personal and professional profile.
 * Contains detailed information about the therapist's background.
 *
 * Relations:
 * - One-to-One with User (each profile belongs to one user)
 */
@Entity
@Table(name = "user_profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    /**
     * User's last name(s)
     */
    @Column(nullable = false, length = 100)
    private String lastName;

    /**
     * User's date of birth
     */
    @Column(nullable = false)
    private LocalDate birthDate;

    /**
     * User's gender identity
     */
// Eliminar el enum interno y usar el nuevo
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender; // Ahora usa el enum ex

    /**
     * Profile creation timestamp
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Year the therapist graduated
     */
    @Column(name = "graduation_year", nullable = false)
    private Integer graduationYear;

    /**
     * Social media links stored as JSON
     */
    //@Column(columnDefinition = "JSON")
   // private Map<String, String> socialMedia;

            //actualizacion 2.0
    @Column(columnDefinition = "JSON")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> socialMedia;

    /**
     * Years of professional experience (0-100)
     */
    @Column(nullable = false)
    private Integer yearsExperience;

    /**
     * Reference to the associated user
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    /**
     * University where the therapist studied
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    public enum Gender {
        MALE, FEMALE, NON_BINARY, OTHER
    }
}