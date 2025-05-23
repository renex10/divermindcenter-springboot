package divermindcenterterapia.divermindcenter.dto;

import divermindcenterterapia.divermindcenter.entity.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

/**
 * DTO (Data Transfer Object) para el formulario de registro de usuarios.
 *
 * Problema original:
 * - Había duplicidad en el campo universityId (tanto en UserRegistrationDTO como en UserProfileDTO)
 * - Esto causaba conflictos de validación y estructura inconsistente
 *
 * Solución implementada:
 * - Se eliminó universityId de UserRegistrationDTO (dejándolo solo en UserProfileDTO)
 * - Se documentaron claramente todas las validaciones
 * - Se organizó la estructura para reflejar correctamente la relación entre entidades
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {

    /* -------------------------------
       DATOS BÁSICOS DEL USUARIO
       ------------------------------- */

    /**
     * Nombre(s) del usuario
     * Validación: No puede estar vacío y máximo 100 caracteres
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String firstName;

    /**
     * Email para inicio de sesión (debe ser único en el sistema)
     * Validación: Formato email válido y máximo 100 caracteres
     */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    @Size(max = 100, message = "El email no puede exceder los 100 caracteres")
    private String email;

    /**
     * Contraseña (será encriptada antes de almacenarse)
     * Validación: Entre 8 y 30 caracteres
     */
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 30, message = "La contraseña debe tener entre 8 y 30 caracteres")
    private String password;

    /**
     * Confirmación de contraseña (debe coincidir con password)
     * Validación: No puede estar vacía
     */
    @NotBlank(message = "La confirmación de contraseña es obligatoria")
    private String confirmPassword;

    /**
     * Rol del usuario en el sistema
     * Validación: No puede ser nulo
     */
    @NotNull(message = "El rol es obligatorio")
    private UserRole role;

    /**
     * RUT en formato chileno
     * Validación: Formato XX.XXX.XXX-X
     */
    @NotBlank(message = "El RUT es obligatorio")
    @Pattern(regexp = "^\\d{1,2}(?:\\.\\d{3}){2}-[\\dkK]$",
            message = "Formato RUT inválido (Ej: 12.345.678-9)")
    private String rut;

    /* -------------------------------
       DATOS DE CONTACTO
       ------------------------------- */

    /**
     * Dirección principal del usuario
     * Validación: Objeto AddressDTO válido y no nulo
     */
    @Valid
    @NotNull(message = "La dirección es obligatoria")
    private AddressDTO address;

    /**
     * Teléfonos asociados (opcional)
     * Validación: Lista de PhoneDTO válidos
     */
    @Valid
    private List<PhoneDTO> phones;

    /* -------------------------------
       INFORMACIÓN PROFESIONAL
       ------------------------------- */

    /**
     * Tipo de práctica profesional
     * Validación: No puede ser nulo
     */
    @NotNull(message = "El tipo de práctica es obligatorio")
    private PracticeType practiceType;

    /**
     * ID del centro de rehabilitación (solo para CENTRO_AFILIADO)
     * Validación: Obligatorio cuando practiceType = CENTRO_AFILIADO
     */
    private Long rehabilitationCenterId;

    /* -------------------------------
       INFORMACIÓN ACADÉMICA Y PERFIL
       ------------------------------- */

    /**
     * Perfil personal del terapeuta
     * Validación: Objeto UserProfileDTO válido y no nulo
     * Contiene:
     * - universityId (obligatorio)
     * - Información biográfica
     * - Redes sociales
     */
    @Valid
    @NotNull(message = "El perfil terapéutico es obligatorio")
    private UserProfileDTO profile;

    /**
     * Información profesional pública
     * Validación: Objeto ProfessionalInfoDTO válido y no nulo
     * Contiene:
     * - Experiencia
     * - Biografía
     * - Modalidad de atención
     */
    @Valid
    @NotNull(message = "La información profesional es obligatoria")
    private ProfessionalInfoDTO professionalInfo;

    @Builder.Default
    private boolean active = true; // Valor por defecto true
}