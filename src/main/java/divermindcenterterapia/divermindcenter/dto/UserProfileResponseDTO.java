package divermindcenterterapia.divermindcenter.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class UserProfileResponseDTO {
    // Datos básicos
    private Long id;
    private String firstName;
    private String email;
    private String rut;

    // Datos de perfil (de UserProfile)
    private String lastName;
    private String birthDate;
    private String gender;
    private String universityName;

    // Datos profesionales (de ProfessionalInfo)
    private String biography;
    private String experienceSummary;
    private String serviceModality;

    // Otros campos personalizados (ej: lista de teléfonos)
    private List<PhoneDTO> phones;
}