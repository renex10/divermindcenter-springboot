package divermindcenterterapia.divermindcenter.service;

import divermindcenterterapia.divermindcenter.dto.BoyDTO;
import divermindcenterterapia.divermindcenter.dto.BoyResponseDTO;
import java.util.List;

/**
 * El Jefe de los Niños - Maneja todas las reglas y operaciones importantes
 * Decide cómo y cuándo se pueden realizar las operaciones con los niños
 */
public interface BoyService {
    // Registra un nuevo niño en el sistema
    BoyResponseDTO createBoy(BoyDTO boyDTO);

    // Obtiene la lista completa de niños registrados
    List<BoyResponseDTO> getAllBoys();

    // Busca un niño específico usando su número de identificación
    BoyResponseDTO getBoyById(Long id);

    // Actualiza la información de un niño existente
    BoyResponseDTO updateBoy(Long id, BoyDTO boyDTO);

    // Cambia el estado activo/inactivo de un niño
    BoyResponseDTO updateBoyStatus(Long id, Boolean isActive, String reason);

    // Elimina permanentemente un registro de niño
    void deleteBoy(Long id);
}