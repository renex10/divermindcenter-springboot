package divermindcenterterapia.divermindcenter.exception;

/**
 * Excepción lanzada cuando se detecta un RUT chileno inválido.
 * Puede ser por formato incorrecto o dígito verificador no válido.
 */
public class InvalidRutException extends RuntimeException {
    private final String rutValue;

    /**
     * Constructor para la excepción
     * @param rutValue RUT que falló la validación
     * @param message Mensaje descriptivo del error
     */
    public InvalidRutException(String rutValue, String message) {
        super(String.format("RUT inválido '%s': %s", rutValue, message));
        this.rutValue = rutValue;
    }

    // Getter
    public String getRutValue() { return rutValue; }
}