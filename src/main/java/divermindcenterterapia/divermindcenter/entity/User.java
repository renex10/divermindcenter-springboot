package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entidad que representa a un usuario del sistema (terapeuta, administrador, etc.)
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre(s) del usuario
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * Email único para inicio de sesión
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Contraseña encriptada
     */
    @Column(nullable = false)
    private String password;

    /**
     * Rol del usuario (TERAPIST, CENTER_ADMIN, etc.)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    /**
     * Documento de identificación (RUT/DNI)
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dni_id", referencedColumnName = "id", nullable = false)
    private Dni dni;

    /**
     *
     * Dirección asociada al usuario
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    /**
     * Teléfonos asociados al usuario
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Phone> phones;

    /**
     * Tipo de práctica (INDEPENDENTE o CENTRO_AFILIADO)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PracticeType practiceType;

    /**
     * Centro de rehabilitación asociado (si es CENTRO_AFILIADO)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rehabilitation_center_id")
    private RehabilitationCenter rehabilitationCenter;
}
