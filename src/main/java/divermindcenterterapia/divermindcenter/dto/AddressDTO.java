package divermindcenterterapia.divermindcenter.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO para la dirección de un usuario
 * Contiene validaciones para los campos de dirección
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    @NotBlank(message = "La calle es obligatoria")
    private String street;

    @NotBlank(message = "El número es obligatorio")
    private String number;

    private String references;

    @NotNull(message = "La comuna es obligatoria")
    private Long communeId; // ID de la comuna en lugar de String
}