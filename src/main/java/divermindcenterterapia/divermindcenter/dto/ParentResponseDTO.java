package divermindcenterterapia.divermindcenter.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

/**
 * FORMATO DE RESPUESTA PARA PADRES
 * ---------------------------------
 * ¿Qué información muestra?
 * - Identificación única
 * - Nombre completo
 * - Correo electrónico
 * - Fecha de registro
 * - Estado de la cuenta (activa/inactiva)
 * - Todos sus números de teléfono
 */
@Data
@Builder
public class ParentResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String rut;
    private String createdAt;
    private boolean isActive;
    private List<String> phones;
}