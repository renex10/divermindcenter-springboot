package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

/**
 * Entidad para centros de rehabilitación
 */
@Entity
@Table(name = "rehabilitation_centers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RehabilitationCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre legal del centro
     */
    @Column(nullable = false)
    private String legalName;

    /**
     * Nombre comercial (marca)
     */
    @Column(nullable = false)
    private String commercialName;

    /**
     * Identificador tributario (RUT/RFC/NIT)
     */
    @Column(unique = true, nullable = false)
    private String taxId;

    /**
     * Dirección del centro
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    /**
     * Terapeutas asociados a este centro
     */
    @OneToMany(mappedBy = "rehabilitationCenter", fetch = FetchType.LAZY)
    private List<User> therapists;
}