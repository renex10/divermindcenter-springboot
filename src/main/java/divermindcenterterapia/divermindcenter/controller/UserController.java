package divermindcenterterapia.divermindcenter.controller;

import divermindcenterterapia.divermindcenter.dto.UserRegistrationDTO;
import divermindcenterterapia.divermindcenter.dto.UserResponseDTO;
import divermindcenterterapia.divermindcenter.exception.*;
import divermindcenterterapia.divermindcenter.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para manejar el registro y gestión de usuarios.
 *
 * Responsabilidades principales:
 * - Recibir solicitudes HTTP relacionadas con usuarios
 * - Delegar el procesamiento al servicio correspondiente
 * - Manejar respuestas y errores
 * - Validar datos de entrada
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO dto) {
        try {
            UserResponseDTO response = userService.registerUser(dto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (InvalidRutException e) {
            return handleException(e, HttpStatus.BAD_REQUEST);
        } catch (DuplicateEmailException e) {
            return handleException(e, HttpStatus.CONFLICT);
        } catch (ResourceNotFoundException | PasswordMismatchException e) {
            return handleException(e, HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<String> handleException(Exception e, HttpStatus status) {
        return ResponseEntity.status(status).body(e.getMessage());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}