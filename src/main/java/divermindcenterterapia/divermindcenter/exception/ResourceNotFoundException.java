package divermindcenterterapia.divermindcenter.exception;

/**
 * Excepción lanzada cuando un recurso solicitado no puede ser encontrado en el sistema.
 * Ideal para operaciones de búsqueda por ID o campos únicos.
 *
 * Ejemplos de uso:
 * 1. Cuando no se encuentra un usuario por ID → new ResourceNotFoundException("Usuario", "id", 123)
 * 2. Cuando no se encuentra una comuna por nombre → new ResourceNotFoundException("Comuna", "nombre", "Santiago")
 *
 * Beneficios:
 * - Proporciona información detallada sobre el error
 * - Facilita el debugging con datos específicos
 * - Mensaje de error claro para frontend y logs
 */
public class ResourceNotFoundException extends RuntimeException {
    private final String resourceName;  // Ej: "Usuario", "Dni", "Universidad"
    private final String fieldName;    // Ej: "id", "rut", "nombre"
    private final Object fieldValue;   // Ej: 123, "12.345.678-9", "Universidad de Chile"

    /**
     * Constructor principal para la excepción
     * @param resourceName Nombre del tipo de recurso no encontrado (en singular y capitalizado)
     * @param fieldName Nombre del campo usado en la búsqueda
     * @param fieldValue Valor específico que no produjo resultados
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s no encontrado con %s: '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    /**
     * Obtiene el nombre del recurso no encontrado
     * @return Nombre del recurso (ej: "Usuario")
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Obtiene el nombre del campo de búsqueda
     * @return Nombre del campo (ej: "id")
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Obtiene el valor buscado que no fue encontrado
     * @return Valor del campo (ej: 123)
     */
    public Object getFieldValue() {
        return fieldValue;
    }
}