// DniServiceImpl.java
package divermindcenterterapia.divermindcenter.service.impl;

import divermindcenterterapia.divermindcenter.dto.DniDTO;
import divermindcenterterapia.divermindcenter.entity.Dni;
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
        // Validación del formato del RUT
        if (!isValidRutChileno(dniDto.getValue())) {
            throw new IllegalArgumentException("Formato de RUT chileno inválido");
        }

        // Conversión y guardado
        Dni dni = mapToEntity(dniDto);
        Dni newDni = dniRepository.save(dni);

        // Retorno del DTO creado
        return mapToDTO(newDni);
    }

    // Métodos requeridos por la interfaz (implementación básica)
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

    // Métodos de conversión
    private DniDTO mapToDTO(Dni dni) {
        return DniDTO.builder()
                .id(dni.getId())
                .value(dni.getValue())
                .build();
    }

    private Dni mapToEntity(DniDTO dniDto) {
        return Dni.builder()
                .id(dniDto.getId())
                .value(dniDto.getValue())
                .build();
    }

    // Validación completa del RUT
    private boolean isValidRutChileno(String rut) {
        if (rut == null || rut.trim().isEmpty()) return false;

        String rutLimpio = rut.replaceAll("[.-]", "").toUpperCase();
        if (rutLimpio.length() < 2) return false;

        String cuerpo = rutLimpio.substring(0, rutLimpio.length() - 1);
        char dv = rutLimpio.charAt(rutLimpio.length() - 1);

        if (!cuerpo.matches("\\d+") || (!Character.isDigit(dv) && dv != 'K')) {
            return false;
        }

        return dv == calcularDigitoVerificador(cuerpo);
    }

    // Cálculo del dígito verificador
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