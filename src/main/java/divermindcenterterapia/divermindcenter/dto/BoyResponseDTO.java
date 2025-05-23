package divermindcenterterapia.divermindcenter.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

/**
 * Objeto de Transferencia de Datos (DTO) para representar la respuesta detallada de un niño.
 *
 * ¿Por qué existe este DTO?
 * 1. Seguridad: Oculta detalles internos de la entidad
 * 2. Formateo: Presenta datos en formato amigable para el frontend
 * 3. Eficiencia: Agrupa información de múltiples fuentes en una sola respuesta
 *
 * Contiene:
 * - Datos básicos del niño
 * - Información procesada (edad calculada)
 * - Relaciones formateadas (nombres en vez de IDs)
 * - Estado del registro
 */
@Data
@Builder
public class BoyResponseDTO {
    /**
     * ID único generado automáticamente al crear el registro
     * Ejemplo: 205 → Identificador único en la base de datos
     */
    private Long id;

    /**
     * Nombre completo formateado: Nombre + Apellido
     * Ejemplo: "Carlos Gómez"
     * Fuente: Combina firstName y lastName de la entidad Boy
     */
    private String fullName;

    /**
     * Fecha de nacimiento original del niño
     * Formato: AAAA-MM-DD (Estándar internacional)
     * Ejemplo: "2018-03-15"
     */
    private LocalDate birthDate;

    /**
     * Edad calculada automáticamente en años
     * Se actualiza dinámicamente cada vez que se consulta
     * Ejemplo: Si nació el 2018-03-15 → En 2024 sería 6
     */
    private Integer age;

    /**
     * Lista de diagnósticos asociados en formato legible
     * Ejemplo: ["TEA (Trastorno Espectro Autista)", "TDAH"]
     * Fuente: Nombres de entidades Diagnostic relacionadas
     */
    private List<String> diagnostics;

    /**
     * Nombre completo del padre/tutor responsable
     * Ejemplo: "María Fernández López"
     * Fuente: Combina firstName y lastName de la entidad Parent
     */
    private String parentName;

    /**
     * Estado actual del registro en el sistema:
     * - true: Activo (puede recibir terapias)
     * - false: Inactivo (registro archivado)
     * Valor por defecto al crear: true
     */
    private Boolean isActive;

    /**
     * Motivo de inactivación (solo presente si isActive = false)
     * Ejemplo: "Familia solicitó baja temporal"
     * Longitud máxima: 500 caracteres
     */
    private String cancellationReason;

    /**
     * Fecha y hora exacta de creación del registro
     * Formato: "15/03/2024 14:30"
     * Generado automáticamente por el sistema
     */
    private String creationDate;

    /**
     * Próxima evaluación programada (si existe)
     * Formato: AAAA-MM-DD
     * Ejemplo: "2024-06-01" → Próximo control médico
     */
    private LocalDate nextEvaluation;

    // -------------------------------
    // ¿Cómo se construye este DTO?
    // -------------------------------
    // 1. El servicio (BoyService) toma la entidad Boy de la base de datos
    // 2. Combina datos de múltiples tablas (Padre, Diagnósticos)
    // 3. Calcula campos derivados (edad, nombre completo)
    // 4. Usa el patrón Builder para crear el DTO
    //
    // Ejemplo de uso en servicio:
    // BoyResponseDTO.builder()
    //     .id(boy.getId())
    //     .fullName(boy.getFirstName() + " " + boy.getLastName())
    //     .age(calcularEdad(boy.getBirthDate()))
    //     .build();
}