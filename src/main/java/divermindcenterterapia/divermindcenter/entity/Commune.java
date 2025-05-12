package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad para comunas/ciudades
 */
@Entity
@Table(name = "communes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commune {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de la comuna/ciudad
     */
    @Column(unique = true, nullable = false)
    private String name;

    /**
     * Región a la que pertenece
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;
}