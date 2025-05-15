package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a therapist's professional information.
 * Used for public display and search filtering by patients.
 *
 * Relations:
 * - One-to-One with UserProfile (each profile has one professional info)
 */
@Entity
@Table(name = "professional_infos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professional_info_id")
    private Long id;

    /**
     * Summary of professional experience (short)
     */
    @Column(nullable = false, length = 255)
    private String experienceSummary;

    /**
     * Detailed biography (max 1500 chars)
     */
    @Column(nullable = false, length = 1500)
    private String biography;

    /**
     * Optional detailed experience description
     */
    @Column(columnDefinition = "TEXT")
    private String detailedExperience;

    /**
     * Service modality preference
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceModality serviceModality;

    /**
     * Reference to the user profile
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false, unique = true)
    private UserProfile userProfile;

    public enum ServiceModality {
        IN_PERSON, ONLINE, HYBRID
    }
}