package divermindcenterterapia.divermindcenter.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) para la entidad Region.
 *
 * Propósito:
 * - Exponer solo los datos necesarios a la API
 * - Evitar exponer la estructura interna de la entidad
 * - Permitir personalizar los datos enviados al cliente
 *
 * Lombok genera automáticamente getters, setters, equals, hashCode y toString
 */
@Data
public class RegionDTO {
    private Long id;             // Identificador único
    private String name;         // Nombre de la región
    private Long countryId;      // ID del país asociado (opcional)
    private String countryName;  // Nombre del país asociado (opcional)
    private String phoneCode;    // Código telefónico del país (opcional)
}