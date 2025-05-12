package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "countries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "phone_code")
    private String phoneCode;
}