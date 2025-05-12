package divermindcenterterapia.divermindcenter.service.impl;

import divermindcenterterapia.divermindcenter.dto.DniDTO;
import divermindcenterterapia.divermindcenter.entity.Dni;
import divermindcenterterapia.divermindcenter.exception.DuplicateRutException;
import divermindcenterterapia.divermindcenter.exception.InvalidRutException;
import divermindcenterterapia.divermindcenter.repository.DniRepository;
import divermindcenterterapia.divermindcenter.service.DniService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DniServiceImpl implements DniService {

    private final DniRepository dniRepository;

    @Override
    public DniDTO createDni(DniDTO dniDto) {
        // ------------------------------------------------------------
        // PASO 1: Validación del formato del RUT chileno
        // ------------------------------------------------------------
        if (!isValidRutChileno(dniDto.getValue())) {
            // Aquí lanzamos excepción si el formato no es válido
            throw new InvalidRutException(dniDto.getValue(), "Formato de RUT inválido");
        }

        // ------------------------------------------------------------
        // PASO 2: Limpieza del RUT (eliminar puntos y guión)
        // ------------------------------------------------------------
        String rutLimpio = cleanRut(dniDto.getValue());

        // ------------------------------------------------------------
        // PASO 3: Verificación de RUT duplicado en base de datos
        // Aquí actualizamos validación rut duplicado
        // ------------------------------------------------------------
        if (dniRepository.existsByValue(rutLimpio)) {
            // Aquí lanzamos excepción si el RUT ya existe
            throw new DuplicateRutException(dniDto.getValue());
        }

        // ------------------------------------------------------------
        // PASO 4: Conversión y guardado del DNI
        // ------------------------------------------------------------
        Dni dni = mapToEntity(dniDto);
        dni.setValue(rutLimpio); // Guardamos el RUT limpio
        Dni newDni = dniRepository.save(dni);

        return mapToDTO(newDni);
    }

    // Métodos de la interfaz (implementación básica)
    @Override
    public DniDTO getDniById(Long id) {
        return dniRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("DNI no encontrado"));
    }

    @Override
    public List<DniDTO> getAllDnis() {
        return dniRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DniDTO updateDni(DniDTO dniDto, Long id) {
        Dni existingDni = dniRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DNI no encontrado"));

        existingDni.setValue(dniDto.getValue());
        return mapToDTO(dniRepository.save(existingDni));
    }

    @Override
    public void deleteDni(Long id) {
        dniRepository.deleteById(id);
    }

    // ------------------------------------------------------------
    // MÉTODOS AUXILIARES
    // ------------------------------------------------------------

    /**
     * Convierte entidad Dni a DTO
     */
    private DniDTO mapToDTO(Dni dni) {
        return DniDTO.builder()
                .id(dni.getId())
                .value(dni.getValue())
                .build();
    }

    /**
     * Convierte DTO a entidad Dni
     */
    private Dni mapToEntity(DniDTO dniDto) {
        return Dni.builder()
                .id(dniDto.getId())
                .value(dniDto.getValue())
                .build();
    }

    /**
     * Limpia el RUT eliminando puntos y guión
     */
    private String cleanRut(String rut) {
        return rut.replaceAll("[.-]", "").toUpperCase();
    }

    /**
     * Valida un RUT chileno según formato y dígito verificador
     */
    private boolean isValidRutChileno(String rut) {
        if (rut == null || rut.trim().isEmpty()) return false;

        String rutLimpio = cleanRut(rut);
        if (rutLimpio.length() < 2) return false;

        String cuerpo = rutLimpio.substring(0, rutLimpio.length() - 1);
        char dv = rutLimpio.charAt(rutLimpio.length() - 1);

        if (!cuerpo.matches("\\d+") || (!Character.isDigit(dv) && dv != 'K')) {
            return false;
        }

        return dv == calcularDigitoVerificador(cuerpo);
    }

    /**
     * Calcula el dígito verificador de un RUT chileno
     */
    private char calcularDigitoVerificador(String numeroStr) {
        int suma = 0;
        int multiplicador = 2;

        for (int i = numeroStr.length() - 1; i >= 0; i--) {
            suma += Character.getNumericValue(numeroStr.charAt(i)) * multiplicador;
            multiplicador = multiplicador == 7 ? 2 : multiplicador + 1;
        }

        int resto = suma % 11;
        int dv = 11 - resto;

        return switch (dv) {
            case 11 -> '0';
            case 10 -> 'K';
            default -> Character.forDigit(dv, 10);
        };
    }
}