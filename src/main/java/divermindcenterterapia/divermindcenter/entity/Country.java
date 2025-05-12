package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad para países
 */
@Entity
@Table(name = "countries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del país
     */
    @Column(unique = true, nullable = false)
    private String name;

    /**
     * Código telefónico internacional (ej: +56 para Chile)
     */
    @Column(name = "phone_code", nullable = false)
    private String phoneCode;
}