package divermindcenterterapia.divermindcenter.exception;

/**
 * Excepción lanzada cuando un recurso solicitado no puede ser encontrado en el sistema.
 * Ideal para operaciones de búsqueda por ID o campos únicos.
 */
public class ResourceNotFoundException extends RuntimeException {
    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    /**
     * Constructor para la excepción
     * @param resourceName Nombre del recurso (ej: "Dni", "Paciente")
     * @param fieldName Nombre del campo de búsqueda (ej: "id", "rut")
     * @param fieldValue Valor buscado que no fue encontrado
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s no encontrado con %s: '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    // Getters
    public String getResourceName() { return resourceName; }
    public String getFieldName() { return fieldName; }
    public Object getFieldValue() { return fieldValue; }
}