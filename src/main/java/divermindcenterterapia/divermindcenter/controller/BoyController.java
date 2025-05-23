
//(El Recepcionista de Internet)
package divermindcenterterapia.divermindcenter.controller;

import divermindcenterterapia.divermindcenter.dto.BoyDTO;
import divermindcenterterapia.divermindcenter.dto.BoyResponseDTO;
import divermindcenterterapia.divermindcenter.service.BoyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/boys")
@RequiredArgsConstructor
public class BoyController {

    private final BoyService boyService;

    // Crear nuevo niño
    @PostMapping
    public ResponseEntity<BoyResponseDTO> crearNiño(@RequestBody BoyDTO boyDTO) {
        BoyResponseDTO nuevoNiño = boyService.createBoy(boyDTO);
        return new ResponseEntity<>(nuevoNiño, HttpStatus.CREATED);
    }

    // Obtener todos los niños
    @GetMapping
    public ResponseEntity<List<BoyResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(boyService.getAllBoys());
    }

    // Obtener un niño por ID
    @GetMapping("/{id}")
    public ResponseEntity<BoyResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(boyService.getBoyById(id));
    }

    // Actualizar información de niño
    @PutMapping("/{id}")
    public ResponseEntity<BoyResponseDTO> actualizarNiño(
            @PathVariable Long id,
            @RequestBody BoyDTO boyDTO) {
        return ResponseEntity.ok(boyService.updateBoy(id, boyDTO));
    }

    // Cambiar estado activo/inactivo
    @PatchMapping("/{id}/status")
    public ResponseEntity<BoyResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestParam Boolean isActive,
            @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(boyService.updateBoyStatus(id, isActive, reason));
    }

    // Eliminar niño
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNiño(@PathVariable Long id) {
        boyService.deleteBoy(id);
        return ResponseEntity.noContent().build();
    }
}