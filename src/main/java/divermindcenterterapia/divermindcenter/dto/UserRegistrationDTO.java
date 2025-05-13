package divermindcenterterapia.divermindcenter.dto;

import divermindcenterterapia.divermindcenter.entity.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

/**
 * DTO (Data Transfer Object) para el formulario de registro de usuarios.
 *
 * Objetivo principal:
 * - Capturar todos los datos necesarios para registrar un nuevo usuario
 * - Validar los datos antes de procesarlos en el backend
 * - Estructurar la información para facilitar el mapeo a entidades JPA
 * - Servir como contrato entre el frontend y backend para el registro
 *
 * Contiene:
 * - Datos personales básicos (nombre, email, contraseña)
 * - Información de identificación (RUT)
 * - Datos de contacto (dirección, teléfonos)
 * - Información profesional (rol, tipo de práctica, centro asociado)
 * - Validaciones integradas para cada campo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {

    /**
     * Nombre(s) del usuario
     * Validaciones:
     * - No puede estar vacío
     * - Máximo 100 caracteres
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String firstName;

    /**
     * Email del usuario (usado para inicio de sesión)
     * Validaciones:
     * - Formato de email válido
     * - No puede estar vacío
     * - Máximo 100 caracteres
     */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    @Size(max = 100, message = "El email no puede exceder los 100 caracteres")
    private String email;

    /**
     * Contraseña del usuario (será encriptada)
     * Validaciones:
     * - No puede estar vacía
     * - Entre 8 y 30 caracteres
     */
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 30, message = "La contraseña debe tener entre 8 y 30 caracteres")
    private String password;

    /**
     * Confirmación de contraseña (debe coincidir con password)
     * Validación:
     * - No puede estar vacía
     */
    @NotBlank(message = "Debe repetir la contraseña")
    private String confirmPassword;

    /**
     * Rol del usuario en el sistema
     * Validación:
     * - No puede ser nulo
     */
    @NotNull(message = "El rol es obligatorio")
    private UserRole role;

    /**
     * RUT del usuario en formato chileno
     * Validaciones:
     * - No puede estar vacío
     * - Formato XX.XXX.XXX-X
     */
    @NotBlank(message = "El RUT es obligatorio")
    @Pattern(regexp = "^\\d{1,2}(?:\\.\\d{3}){2}-[\\dkK]$",
            message = "Formato RUT inválido (Ej: 12.345.678-9)")
    private String rut;

    /**
     * Dirección del usuario
     * Validación:
     * - No puede ser nula
     * - Se validan los campos internos del AddressDTO
     */
    @Valid
    @NotNull(message = "La dirección es obligatoria")
    private AddressDTO address;

    /**
     * Teléfonos del usuario (lista opcional)
     * Validación:
     * - Se validan los campos internos de cada PhoneDTO
     */
    @Valid
    private List<PhoneDTO> phones;

    /**
     * Tipo de práctica profesional
     * Validación:
     * - No puede ser nulo
     */
    @NotNull(message = "Debe especificar el tipo de práctica")
    private PracticeType practiceType;

    /**
     * ID del centro de rehabilitación (solo si practiceType = CENTRO_AFILIADO)
     * Validación:
     * - Obligatorio cuando practiceType es CENTRO_AFILIADO
     */
    private Long rehabilitationCenterId;
}