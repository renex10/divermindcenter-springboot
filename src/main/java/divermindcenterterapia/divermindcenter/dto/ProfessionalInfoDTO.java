package divermindcenterterapia.divermindcenter.dto;

import divermindcenterterapia.divermindcenter.entity.ProfessionalInfo.ServiceModality;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalInfoDTO {
    @NotBlank(message = "Resumen de experiencia es requerido")
    @Size(max = 255, message = "El resumen no puede exceder 255 caracteres")
    private String experienceSummary;

    @NotBlank(message = "Biografía es requerida")
    @Size(max = 1500, message = "La biografía no puede exceder 1500 caracteres")
    private String biography;

    private String detailedExperience;

    @NotNull(message = "Modalidad de servicio es requerida")
    private ServiceModality serviceModality;
}