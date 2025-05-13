package divermindcenterterapia.divermindcenter.dto;

import lombok.*;

/**
 * DTO para números telefónicos
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO {

    private Long id;

    /**
     * Número telefónico (sin código de país)
     */
    private String phoneNumber;

    /**
     * Tipo de teléfono (Móvil, Casa, Trabajo, etc.)
     */
    private String phoneType;

    /**
     * Identificador del país asociado al código telefónico
     */
    private Long countryId;

    /**
     * Identificador del usuario al que pertenece este teléfono
     */
    private Long userId;
}
