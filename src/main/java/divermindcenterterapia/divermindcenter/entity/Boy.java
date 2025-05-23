package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import java.time.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa a un niño/paciente masculino en el sistema.
 * Contiene información personal, médica y relaciones con padres/terapeutas.
 */
@Entity
@Table(name = "boys")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Boy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boy_id")
    private Long id;

    @Column(nullable = false, length = 255)
    private String firstName;

    @Column(nullable = false, length = 255)
    private String lastName;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(columnDefinition = "TEXT")
    private String diagnosisNotes;

    @Column(columnDefinition = "JSON")
    @JdbcTypeCode(SqlTypes.JSON)
    private String specificNeeds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Parent parent;

    @OneToOne
    @JoinColumn(unique = true)
    private Dni dni;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User therapist;

    @ManyToOne(fetch = FetchType.LAZY)
    private RehabilitationCenter rehabilitationCenter;

    private LocalTime registrationTime;
    private LocalDate nextEvaluationDate;
    private LocalDateTime lastSessionDate;


    // ▼▼▼▼ Relación CORRECTA (dejar solo esta) ▼▼▼▼
    @ManyToMany
    @JoinTable(
            name = "boy_diagnostics",
            joinColumns = @JoinColumn(name = "boy_id"),
            inverseJoinColumns = @JoinColumn(name = "diagnostic_id")
    )
    @Builder.Default
    private Set<Diagnostic> diagnostics = new HashSet<>();

    //    Tipo Boolean para manejar estados nulos
//
//    Valor por defecto true usando @Builder.Default
//
//    Restricción NOT NULL en base de datos
//
//    Configuración de columna para compatibilidad con diferentes motores de BD
    @Builder.Default
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive = true;  // Nuevo campo de estado

    // Campo de cancelación (opcional)
    @Column(columnDefinition = "TEXT")
    private String cancellationReason;  // Motivo de inactivación



}