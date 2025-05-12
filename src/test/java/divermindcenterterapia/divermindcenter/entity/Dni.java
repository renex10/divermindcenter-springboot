package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dnis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    @OneToOne(mappedBy = "dni")
    private User user;
}