package divermindcenterterapia.divermindcenter.controller;

import divermindcenterterapia.divermindcenter.dto.RegionDTO;
import divermindcenterterapia.divermindcenter.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para manejar operaciones con regiones.
 *
 * Responsabilidades:
 * - Mapear endpoints HTTP a operaciones del servicio
 * - Manejar solicitudes/respuestas HTTP
 * - Delegar lógica de negocio al servicio correspondiente
 */
@RestController
@RequestMapping("/api/regions") // Ruta base para todos los endpoints de este controlador
@RequiredArgsConstructor // Inyección automática de dependencias
@CrossOrigin(origins = "*") // Permite solicitudes CORS desde cualquier origen
public class RegionController {

    // Inyección del servicio de regiones
    private final RegionService regionService;

    /**
     * Endpoint GET para obtener todas las regiones.
     *
     * Flujo:
     * 1. Recibe solicitud GET en /api/regions
     * 2. Delega al servicio para obtener los datos
     * 3. Retorna lista de regiones en formato JSON
     *
     * @return Lista de todas las regiones
     */
    @GetMapping
    public List<RegionDTO> getAllRegions() {
        return regionService.getAllRegions();
    }

    /**
     * Endpoint POST para crear una nueva región.
     *
     * Flujo:
     * 1. Recibe solicitud POST en /api/regions con datos JSON
     * 2. Valida y convierte automáticamente el JSON a RegionDTO
     * 3. Delega al servicio la creación de la región
     * 4. Retorna la región creada en formato JSON
     *
     * @param regionDTO Datos de la región a crear
     * @return Región creada con su ID asignado
     */
    @PostMapping
    public RegionDTO createRegion(@RequestBody RegionDTO regionDTO) {
        return regionService.createRegion(regionDTO);
    }
}