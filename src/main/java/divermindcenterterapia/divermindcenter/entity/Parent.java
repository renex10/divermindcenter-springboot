package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ENTIDAD PADRE - GUARDA LA INFORMACIÓN DE LOS PADRES/TUTORES
 * -----------------------------------------------------------
 *
 * ¿Qué contiene?
 * - Datos personales (nombre, apellido, género)
 * - Información de contacto (email, teléfonos)
 * - Seguridad (contraseña, verificación)
 * - Relaciones con otras tablas (dirección, documento, etc.)
 * - ¡NUEVO! Fecha/hora de creación automática
 */
@Entity
@Table(name = "parents") // Nombre de la tabla en la base de datos
@Data // Crea getters, setters y toString automáticamente
@Builder // Permite crear objetos fácilmente: Parent.builder().nombre(...).build()
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
public class Parent {

    // 🔑 ID único generado automáticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parentId;

    // 👨👩 DATOS PERSONALES
    @Column(nullable = false) // Obligatorio
    private String firstName; // Ej: "María"

    @Column(nullable = false)
    private String lastName; // Ej: "González"

    @Enumerated(EnumType.STRING) // Guarda el texto (ej: "FEMALE")
    @Column(nullable = false)
    private Gender gender; // Masculino/Femenino/Otro

    // 🌎 NACIONALIDAD (Relación con tabla Country)
    @ManyToOne(fetch = FetchType.LAZY) // Carga solo cuando se usa
    @JoinColumn(name = "country_id", nullable = false)
    private Country nationality;

    // 🔐 SEGURIDAD
    @Column(unique = true, nullable = false) // Email único y obligatorio
    private String email; // Ej: "usuario@correo.com"

    @Column(nullable = false) // Contraseña encriptada
    private String passwordHash;

    private Boolean isActive = true; // Cuenta activa/inactiva
    private LocalDateTime lastLogin; // Último acceso
    private Boolean emailVerified = false; // ¿Verificó su email?

    // 🏠 DIRECCIÓN Y DOCUMENTO (Relaciones 1-a-1)
    @OneToOne(cascade = CascadeType.ALL) // Si se borra el padre, se borra su dirección
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dni_id", nullable = false)
    private Dni dni; // Documento de identidad

    // 👤 QUIÉN REGISTRÓ AL PADRE (Relación con User)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_by", nullable = false)
    private User registeredBy;

    // 📱 TELÉFONOS (Un padre puede tener varios)
    @OneToMany(
            mappedBy = "parent",
            cascade = CascadeType.ALL, // Si se borra el padre, se borran sus teléfonos
            orphanRemoval = true // Si se elimina un teléfono de la lista, se borra de la BD
    )
    private List<Phone> phones;

    // ⏰ FECHA/HORA DE CREACIÓN (¡NUEVO!)
    @CreationTimestamp // Genera automáticamente la fecha al crear el registro
    @Column(
            name = "created_at", // Nombre en la base de datos
            nullable = false, // Obligatorio
            updatable = false // No se puede modificar después
    )
    private LocalDateTime createdAt; // Ej: "2025-05-17T14:30:00"
}