package divermindcenterterapia.divermindcenter.service.impl;
import divermindcenterterapia.divermindcenter.dto.BoyDTO;
import divermindcenterterapia.divermindcenter.dto.BoyResponseDTO;
import divermindcenterterapia.divermindcenter.entity.*;
import divermindcenterterapia.divermindcenter.exception.ResourceNotFoundException;
import divermindcenterterapia.divermindcenter.repository.*;
import divermindcenterterapia.divermindcenter.service.BoyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoyServiceImpl implements BoyService {

    // Conexión con el almacén de niños
    private final BoyRepository boyRepository;

    // Conexión con el almacén de padres
    private final ParentRepository parentRepository;

    // Conexión con el almacén de diagnósticos
    private final DiagnosticRepository diagnosticRepository;

    @Override
    @Transactional
    public BoyResponseDTO createBoy(BoyDTO boyDTO) {
        // Paso 1: Verificar que el padre existe
        Parent padre = parentRepository.findById(boyDTO.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("Padre", "id", boyDTO.getParentId()));

        // Paso 2: Obtener todos los diagnósticos válidos
        Set<Diagnostic> diagnosticos = new HashSet<>(diagnosticRepository.findAllById(boyDTO.getDiagnosticIds()));

        // Paso 3: Construir el nuevo niño
        Boy nuevoNiño = Boy.builder()
                .firstName(boyDTO.getFirstName())
                .lastName(boyDTO.getLastName())
                .birthDate(boyDTO.getBirthDate())
                .parent(padre)
                .diagnostics(diagnosticos)
                .isActive(true) // Todos nuevos empiezan activos
                .build();

        // Paso 4: Guardar en la base de datos
        Boy niñoGuardado = boyRepository.save(nuevoNiño);

        // Paso 5: Convertir a formato de respuesta
        return convertirARespuesta(niñoGuardado);
    }


    @Override
    public List<BoyResponseDTO> getAllBoys() {
        /* Obtener todos los niños y convertir a formato amigable */
        return boyRepository.findAll().stream()
                .map(this::convertirARespuesta)
                .collect(Collectors.toList());
    }

    @Override
    public BoyResponseDTO getBoyById(Long id) {
        /* Buscar niño por ID o mostrar error si no existe */
        Boy niño = boyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Niño", "id", id));
        return convertirARespuesta(niño);
    }

    @Override
    @Transactional
    public BoyResponseDTO updateBoy(Long id, BoyDTO boyDTO) {
        /* Paso 1: Encontrar el niño existente */
        Boy niñoExistente = boyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Niño", "id", id));

        /* Paso 2: Actualizar información básica */
        niñoExistente.setFirstName(boyDTO.getFirstName());
        niñoExistente.setLastName(boyDTO.getLastName());
        niñoExistente.setBirthDate(boyDTO.getBirthDate());

        /* Paso 3: Actualizar diagnósticos si es necesario */
        if(boyDTO.getDiagnosticIds() != null) {
            Set<Diagnostic> nuevosDiagnosticos = new HashSet<>(diagnosticRepository.findAllById(boyDTO.getDiagnosticIds()));
            niñoExistente.setDiagnostics(nuevosDiagnosticos);
        }

        /* Paso 4: Guardar cambios */
        Boy niñoActualizado = boyRepository.save(niñoExistente);

        /* Paso 5: Devolver versión actualizada */
        return convertirARespuesta(niñoActualizado);
    }

    @Override
    @Transactional
    public BoyResponseDTO updateBoyStatus(Long id, Boolean isActive, String reason) {
        /* Encontrar niño y cambiar su estado */
        Boy niño = boyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Niño", "id", id));

        niño.setIsActive(isActive);
        niño.setCancellationReason(isActive ? null : reason); // Limpiar motivo si se reactiva

        return convertirARespuesta(boyRepository.save(niño));
    }

    @Override
    @Transactional
    public void deleteBoy(Long id) {
        /* Verificar existencia y eliminar */
        if(!boyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Niño", "id", id);


            //.orElseThrow(() -> new ResourceNotFoundException("Centro de rehabilitación", "id", dto.getRehabilitationCenterId()));
        }
        boyRepository.deleteById(id);
    }

    private BoyResponseDTO convertirARespuesta(Boy boy) {
        /* Transformar los datos técnicos a formato fácil de entender */
        return BoyResponseDTO.builder()
                .id(boy.getId())
                .fullName(boy.getFirstName() + " " + boy.getLastName())
                .birthDate(boy.getBirthDate())
                .age(calcularEdad(boy.getBirthDate()))
                .diagnostics(boy.getDiagnostics().stream()
                        .map(Diagnostic::getName)
                        .collect(Collectors.toList()))
                .parentName(boy.getParent().getFirstName() + " " + boy.getParent().getLastName())
                .isActive(boy.getIsActive())
                .cancellationReason(boy.getCancellationReason())
                .build();
    }

    private int calcularEdad(LocalDate fechaNacimiento) {
        /* Calcular edad exacta en años */
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
}