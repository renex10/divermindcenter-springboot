package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad que representa a un usuario del sistema (terapeuta, administrador, etc.)
 * Almacena toda la información personal y profesional del usuario.
 */
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre(s) del usuario
     * No puede ser nulo y es obligatorio para el registro
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * Email único para inicio de sesión
     * Debe ser único en el sistema y no nulo
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Contraseña encriptada del usuario
     * Se almacena en formato hash y no puede ser nula
     */
    @Column(nullable = false)
    private String password;

    /**
     * Rol del usuario en el sistema
     * Define los permisos y acceso del usuario
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    /**
     * Documento de identificación (RUT/DNI)
     * Relación uno-a-uno con cascada para persistencia automática
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dni_id", referencedColumnName = "id", nullable = false)
    private Dni dni;

    /**
     * Dirección asociada al usuario
     * Relación uno-a-uno con cascada para persistencia automática
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    /**
     * Teléfonos asociados al usuario
     * Relación uno-a-many con cascada para persistencia automática
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Phone> phones;

    /**
     * Tipo de práctica profesional
     * Define si trabaja de forma independiente o en un centro
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PracticeType practiceType;

    /**
     * Centro de rehabilitación asociado (si es CENTRO_AFILIADO)
     * Relación many-to-one con carga perezosa
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rehabilitation_center_id")
    private RehabilitationCenter rehabilitationCenter;

    // Add to User.java entity:
    /**
     * University where the therapist studied
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    /**
     * Therapist's profile information
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfile profile;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}