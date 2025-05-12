package divermindcenterterapia.divermindcenter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DniDTO {
    private Long id;
    private String value; // Almacena el RUT en formato completo (ej: 12.345.678-9)
}