// UserProfileDTO.java
package divermindcenterterapia.divermindcenter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import divermindcenterterapia.divermindcenter.entity.UserProfile;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    @NotBlank(message = "Last name is required")
    private String lastName;



    @NotNull(message = "Birth date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull(message = "Gender is required")
    private UserProfile.Gender gender;

    @NotNull(message = "Graduation year is required")
    @Min(value = 1900, message = "Graduation year must be after 1900")
    @Max(value = 2100, message = "Graduation year must be before 2100")
    private Integer graduationYear;

    private Map<String, String> socialMedia;

    @NotNull(message = "Years of experience is required")
    @Min(value = 0, message = "Experience cannot be negative")
    @Max(value = 100, message = "Experience cannot exceed 100 years")
    private Integer yearsExperience;

    @NotNull(message = "University is required")
    private Long universityId;
}