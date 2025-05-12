package divermindcenterterapia.divermindcenter.dto;

import lombok.Data;

@Data
public class CommuneDTO {
    private Long id;
    private String name;
    private RegionDTO region; // O solo regionName, según lo que necesites mostrar
}
