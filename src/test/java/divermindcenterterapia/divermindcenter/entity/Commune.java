package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "communes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;
}