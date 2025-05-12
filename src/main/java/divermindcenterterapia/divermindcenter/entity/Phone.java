package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad para números telefónicos
 */
@Entity
@Table(name = "phones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Número telefónico (sin código de país)
     */
    @Column(name = "number", nullable = false)
    private String phoneNumber;

    /**
     * Tipo de teléfono (Móvil, Casa, Trabajo, etc.)
     */
    @Column(nullable = false)
    private String phoneType;

    /**
     * País asociado al código telefónico
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    /**
     * Usuario al que pertenece este teléfono
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}