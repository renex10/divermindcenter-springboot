package divermindcenterterapia.divermindcenter.dto;

import lombok.Data;
import lombok.Builder;
import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
public class ParentRegistrationDTO {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    private String rut;

    @NotNull
    private AddressRegistrationDTO address;

    @NotEmpty
    private List<PhoneDTO> phones;

    @NotNull
    private Long countryId;

    @NotNull
    private Long registeredByUserId;

    @NotBlank(message = "El género es obligatorio")
    private String gender;  // O usa un Enum si prefieres

    @Data
    @Builder
    public static class AddressRegistrationDTO {
        @NotBlank
        private String street;

        @NotBlank
        private String number;

        private String references;

        @NotNull
        private Long communeId;
    }

    @Data
    @Builder
    public static class PhoneDTO {
        @NotBlank
        private String phoneNumber;

        @NotBlank
        private String phoneType;

        @NotNull
        private Long countryId;
    }
}