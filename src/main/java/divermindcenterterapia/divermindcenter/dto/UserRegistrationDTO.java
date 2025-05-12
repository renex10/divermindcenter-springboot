package divermindcenterterapia.divermindcenter.dto;

import divermindcenterterapia.divermindcenter.entity.PracticeType;
import divermindcenterterapia.divermindcenter.entity.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String firstName;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "Debe repetir la contraseña")
    private String confirmPassword;

    @NotNull(message = "El rol es obligatorio")
    private UserRole role;

    @NotBlank(message = "El RUT es obligatorio")
    private String rut; // Formato: 12.345.678-9

    @Valid
    @NotNull(message = "La dirección es obligatoria")
    private AddressDTO address;

    @Valid
    private List<PhoneDTO> phones; // Opcional

    @NotNull(message = "Debe especificar el tipo de práctica")
    private PracticeType practiceType;

    private Long rehabilitationCenterId; // Obligatorio solo si practiceType = CENTRO_AFILIADO
}