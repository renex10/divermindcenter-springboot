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
    private Long id;                 // Número único del padre (ej: 15)
    private String fullName;        // Nombre completo (ej: "Ana López")
    private String email;           // Correo registrado (ej: "ana@correo.com")
    private String createdAt;       // Fecha de creación (ej: "17/05/2025 14:30")
    private boolean isActive;       // ¿La cuenta está activa? (✔️ Sí / ❌ No)
    private List<String> phones;    // Teléfonos (ej: ["+56 9 1234 5678", "+56 2 2345 6789"])
}