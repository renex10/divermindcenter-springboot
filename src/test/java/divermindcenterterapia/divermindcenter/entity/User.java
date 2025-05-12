package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Transient
    private String passwordConfirmation;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne
    @JoinColumn(name = "dni_id")
    private Dni dni;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Phone> phones;

    @Enumerated(EnumType.STRING)
    private PracticeType practiceType;

    @ManyToOne
    @JoinColumn(name = "rehabilitation_center_id")
    private RehabilitationCenter rehabilitationCenter;
}