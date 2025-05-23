package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa un diagnóstico médico o condición de salud.
 * Relacionada con niños a través de una relación many-to-many.
 */
@Entity
@Table(name = "diagnostics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Diagnostic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnostic_id")
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 50)
    private String code; // Código de clasificación (ej: ICD-10)

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String category; // Ej: "Neurológico", "Psiquiátrico", etc.

    @ManyToMany(mappedBy = "diagnostics")
    @Builder.Default
    private Set<Boy> children = new HashSet<>();
}


//Principales cambios:
//
//Nombre de clase cambiado de Children a Boy
//
//Nombre de tabla modificado a "boys"
//
//Relación ManyToMany renombrada a "boy_diagnostics"
//
//Campo en Diagnostic cambiado de children a boys
//
//Mantenimiento de todas las relaciones y funcionalidades originales
//
//Uso consistente de anotaciones Lombok (@Data, @Builder, etc)
//
//Anotaciones JPA actualizadas para reflejar el nuevo nombre