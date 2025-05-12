package divermindcenterterapia.divermindcenter.controller;

import divermindcenterterapia.divermindcenter.dto.DniDTO;
import divermindcenterterapia.divermindcenter.exception.InvalidRutException;
import divermindcenterterapia.divermindcenter.exception.ResourceNotFoundException;
import divermindcenterterapia.divermindcenter.service.DniService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para manejar operaciones relacionadas con RUTs/DNIs chilenos.
 * Expone endpoints para la creación y validación de documentos de identidad chilenos.
 *
 * <p>Mapeado a la ruta base '/api/dni'</p>
 */
@RestController
@RequestMapping("/api/dni")
@RequiredArgsConstructor
public class DniController {

    private final DniService dniService;

    /**
     * Endpoint para crear un nuevo registro de RUT/DNI chileno.
     * Valida el formato y dígito verificador antes de persistir.
     *
     * @param dniDTO Objeto DTO que contiene el RUT a registrar (formato: XX.XXX.XXX-X)
     * @return ResponseEntity con el DNI creado y estado HTTP 201 (CREATED)
     * @throws InvalidRutException Si el RUT no cumple con el formato chileno estándar
     *         o tiene un dígito verificador incorrecto
     *
     * @apiNote Ejemplo de solicitud:
     * <pre>
     * POST /api/dni
     * {
     *   "value": "12.345.678-9"
     * }
     * </pre>
     */
    @PostMapping
    public ResponseEntity<DniDTO> createDni(@Valid @RequestBody DniDTO dniDTO) {
        DniDTO createdDni = dniService.createDni(dniDTO);
        return new ResponseEntity<>(createdDni, HttpStatus.CREATED);
    }

    /**
     * Manejador de excepciones para RUTs inválidos.
     *
     * @param ex Excepción InvalidRutException capturada
     * @return ResponseEntity con mensaje de error descriptivo y estado HTTP 400 (BAD REQUEST)
     */
    @ExceptionHandler(InvalidRutException.class)
    public ResponseEntity<String> handleInvalidRut(InvalidRutException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    /**
     * Manejador de excepciones para recursos no encontrados.
     *
     * @param ex Excepción ResourceNotFoundException capturada
     * @return ResponseEntity con mensaje de error descriptivo y estado HTTP 404 (NOT FOUND)
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}