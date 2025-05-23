package divermindcenterterapia.divermindcenter.dto;

import divermindcenterterapia.divermindcenter.entity.Boy;
import divermindcenterterapia.divermindcenter.entity.Diagnostic;
import divermindcenterterapia.divermindcenter.entity.Parent;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

/**
 * DTO para el registro y actualización de niños en el sistema.
 * Contiene todas las validaciones necesarias y reglas de negocio.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoyDTO {

    // -------------------------------------
    // Información Básica del Niño
    // -------------------------------------

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    private String lastName;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate birthDate;

    // -------------------------------------
    // Relaciones Obligatorias
    // -------------------------------------

    @NotNull(message = "El ID del padre/tutor es obligatorio")
    private Long parentId;  // Relación con Parent

    @NotNull(message = "El ID del documento es obligatorio")
    private Long dniId;     // Relación con Dni

    // -------------------------------------
    // Información Médica y Diagnósticos
    // -------------------------------------

    @Size(max = 2000, message = "Las notas de diagnóstico no pueden exceder los 2000 caracteres")
    private String diagnosisNotes;

    @NotEmpty(message = "Debe seleccionar al menos un diagnóstico")
    private Set<Long> diagnosticIds;  // IDs de Diagnosticos relacionados

    // -------------------------------------
    // Configuración de Terapia
    // -------------------------------------

    private Long therapistId;         // ID de User (terapeuta asignado)
    private Long rehabCenterId;       // ID de RehabilitationCenter
    private LocalDate nextEvaluationDate;
    private LocalTime registrationTime;

    // -------------------------------------
    // Estado y Seguridad
    // -------------------------------------

    @Builder.Default
    private Boolean isActive = true;  // Valor por defecto al crear

    @Size(max = 500, message = "El motivo de cancelación no puede exceder los 500 caracteres")
    private String cancellationReason;

    // -------------------------------------
    // Métodos de Conversión
    // -------------------------------------

    /**
     * Convierte el DTO a entidad Boy para persistencia.
     * Nota: Las relaciones se deben establecer externamente.
     */
    public static Boy toEntity(BoyDTO dto) {
        return Boy.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .diagnosisNotes(dto.getDiagnosisNotes())
                .nextEvaluationDate(dto.getNextEvaluationDate())
                .registrationTime(dto.getRegistrationTime())
                .isActive(dto.getIsActive())
                .cancellationReason(dto.getCancellationReason())
                .build();
    }

    /**
     * Actualiza una entidad existente con los valores del DTO.
     * Conserva las relaciones existentes no modificadas en el DTO.
     */
    public static void updateEntity(Boy target, BoyDTO source) {
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setBirthDate(source.getBirthDate());
        target.setDiagnosisNotes(source.getDiagnosisNotes());
        target.setNextEvaluationDate(source.getNextEvaluationDate());
        target.setRegistrationTime(source.getRegistrationTime());
        target.setIsActive(source.getIsActive());
        target.setCancellationReason(source.getCancellationReason());
    }
}