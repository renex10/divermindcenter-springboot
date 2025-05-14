package divermindcenterterapia.divermindcenter.dto;

import lombok.*;

import java.util.List;

/**
 * Objeto que representa una página de usuarios en el sistema.
 * Contiene:
 * - Lista de usuarios para la página actual
 * - Información de paginación (página actual, total de páginas, etc.)
 */
@Builder
@Data
public class UserPageResponseDTO {
    private List<UserResponseDTO> users;       // Lista de usuarios (10 máximo por página)
    private int currentPage;                   // Página actual que se está viendo (ej: 1)
    private int totalPages;                    // Total de páginas disponibles
    private long totalUsers;                   // Cantidad total de usuarios en el sistema
}