package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a University where therapists studied.
 * Stores university information including name, address, and website URL.
 *
 * Relations:
 * - Many-to-One with Address (each university has one address)
 * - One-to-Many with User (many therapists can come from one university)
 */
@Entity
@Table(name = "universities") // Plural name following convention
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id") // Explicit column name
    private Long id;

    /**
     * University name - must be unique
     */
    @Column(name = "university_name", nullable = false, unique = true, length = 100)
    private String name;

    /**
     * Physical address of the university
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    /**
     * Official website URL with format validation
     */
    @Column(name = "website_url", nullable = false, length = 200)
    private String websiteUrl;
}