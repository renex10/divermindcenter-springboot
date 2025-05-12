package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad para regiones/estados/provincias
 */
@Entity
@Table(name = "regions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de la región
     */
    @Column(unique = true, nullable = false)
    private String name;

    /**
     * País al que pertenece
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;
}