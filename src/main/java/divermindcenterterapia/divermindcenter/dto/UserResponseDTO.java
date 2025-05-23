package divermindcenterterapia.divermindcenter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import divermindcenterterapia.divermindcenter.entity.UserRole;
import divermindcenterterapia.divermindcenter.entity.PracticeType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO para representar la respuesta de un usuario después del registro
 * Excluye información sensible como contraseñas y datos internos
 */
@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String email;
    private UserRole role;
    private String rut;
    private PracticeType practiceType;
    private Long rehabilitationCenterId;
    private Long universityId; // Nuevo campo

    // UserResponseDTO.java
    private boolean active;
    /**
     * Fecha de creación en formato legible (dd/MM/yyyy)
     * Creation date in human-readable format (dd/MM/yyyy)
     */
    private String creationDate;


    /**
     * Hora de creación en formato legible (HH:mm)
     * Creation time in human-readable format (HH:mm)
     */
    private String creationTime;
}