package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad para direcciones físicas
 */
@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Calle o avenida
     */
    @Column(nullable = false)
    private String street;

    /**
     * Número exterior
     */
    @Column(nullable = false)
    private String number;


    /**
     * Referencias adicionales (opcional)
     */
    @Column(name = "additional_references") // Evita conflictos con SQL
    private String references;

    /**
     * Comuna asociada
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commune_id", nullable = false)
    private Commune commune;
}