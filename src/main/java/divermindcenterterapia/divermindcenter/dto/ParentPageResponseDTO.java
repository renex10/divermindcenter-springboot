// 📦 Archivo: ParentPageResponseDTO.java
package divermindcenterterapia.divermindcenter.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ParentPageResponseDTO {
    private List<ParentResponseDTO> parents; // Lista de padres
    private int currentPage;    // Página actual (ej: 1 de 5)
    private int totalPages;     // Total de páginas
    private long totalParents;  // Cantidad total de padres
}