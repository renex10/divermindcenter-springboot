package divermindcenterterapia.divermindcenter.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import java.util.Objects;

/**
 * Entidad que representa un Documento Nacional de Identidad (DNI/RUT).
 *
 * <p>Relaciones:</p>
 * <ul>
 *   <li><strong>User</strong>: Relación uno-a-uno bidireccional (opcional)</li>
 * </ul>
 *
 * <p>Anotaciones Lombok:</p>
 * <ul>
 *   <li><strong>@Data</strong>: Genera getters, setters, toString, equals y hashCode</li>
 *   <li><strong>@Builder</strong>: Permite construcción fluida</li>
 *   <li><strong>@NoArgsConstructor</strong>: Constructor sin argumentos requerido por JPA</li>
 *   <li><strong>@AllArgsConstructor</strong>: Constructor con todos los campos</li>
 * </ul>
 */
@Entity
@Table(name = "dnis") // Buen práctica: nombre de tabla en plural
@Data // Lombok: genera getters, setters, equals, hashCode, toString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dni {

    /**
     * ID único generado automáticamente.
     * <p>Estrategia: Generación por identidad (autoincremental)</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Valor del documento (formato completo).
     * <p>Restricciones:</p>
     * <ul>
     *   <li>Único en el sistema</li>
     *   <li>No nulo</li>
     *   <li>Ejemplo formato chileno: "12.345.678-9"</li>
     * </ul>
     */
    @Column(unique = true, nullable = false)
    private String value;

    /**
     * Relación inversa con User (opcional).
     * <p>Características:</p>
     * <ul>
     *   <li>Relación uno-a-uno bidireccional</li>
     *   <li>Propiedad "mappedBy" indica que User es el dueño de la relación</li>
     *   <li>Carga perezosa (LAZY) por defecto en relaciones @OneToOne</li>
     * </ul>
     */
    @OneToOne(mappedBy = "dni")
    private User user;

    /**
     * Implementación segura de equals() para entidades JPA.
     * <p>Considera:</p>
     * <ul>
     *   <li>Solo compara el ID cuando ambos objetos son persistentes</li>
     *   <li>Compara campos cuando uno es transitorio</li>
     *   <li>Maneja proxies de Hibernate</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        // Manejo de proxies de Hibernate
        Class<?> oEffectiveClass = (o instanceof HibernateProxy)
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();

        Class<?> thisEffectiveClass = (this instanceof HibernateProxy)
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) return false;

        Dni dni = (Dni) o;
        return getId() != null && Objects.equals(getId(), dni.getId());
    }

    /**
     * Implementación segura de hashCode() para entidades JPA.
     * <p>Características:</p>
     * <ul>
     *   <li>Consistente con equals()</li>
     *   <li>Usa solo campos inmutables (ID)</li>
     *   <li>Devuelve valor constante para objetos no persistentes</li>
     * </ul>
     */
    @Override
    public int hashCode() {
        return getClass().hashCode(); // Solución segura para entidades JPA
    }

    /**
     * Implementación de toString() que evita referencias circulares.
     * <p>Excluye la relación bidireccional con User.</p>
     */
    @Override
    public String toString() {
        return "Dni{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}