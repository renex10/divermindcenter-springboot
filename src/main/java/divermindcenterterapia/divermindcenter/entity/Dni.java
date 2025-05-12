package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String value;

    // Relación inversa opcional (si es necesaria)
    @OneToOne(mappedBy = "dni")
    private User user;
}