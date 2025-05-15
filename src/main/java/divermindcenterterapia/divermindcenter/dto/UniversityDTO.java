// UniversityDTO.java
package divermindcenterterapia.divermindcenter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniversityDTO {
    private Long id;

    @NotBlank(message = "University name is required")
    private String name;

    @NotNull(message = "Address is required")
    private Long addressId;

    @NotBlank(message = "Website URL is required")
    @Pattern(regexp = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?$",
            message = "Invalid website URL format")
    private String websiteUrl;
}