package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "rehabilitation_centers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RehabilitationCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String legalName;
    private String taxId;
    private String commercialName;
    private String contactEmail;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "rehabilitationCenter")
    private List<User> therapists;
}