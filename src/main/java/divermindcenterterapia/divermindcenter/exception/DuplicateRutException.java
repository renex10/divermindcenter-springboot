package divermindcenterterapia.divermindcenter.exception;

import lombok.Getter;

/**
 * Excepción lanzada cuando se intenta registrar un RUT que ya existe en la base de datos.
 * Esta excepción se activa específicamente en la validación de duplicados.
 */
@Getter
public class DuplicateRutException extends RuntimeException {
    /**
     * -- GETTER --
     *  Obtiene el RUT que causó la excepción
     *
     * @return RUT en formato original
     */
    private final String rutValue;

    /**
     * Constructor principal de la excepción
     * @param rutValue RUT que ya existe en la base de datos
     */
    public DuplicateRutException(String rutValue) {
        // Aquí personalizamos el mensaje de error para RUT duplicado
        super(String.format("El RUT '%s' ya existe en la base de datos y no puede duplicarse", rutValue));
        this.rutValue = rutValue;
    }

}