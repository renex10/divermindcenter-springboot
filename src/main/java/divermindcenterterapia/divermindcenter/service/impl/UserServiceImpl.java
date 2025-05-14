package divermindcenterterapia.divermindcenter.service.impl;

import divermindcenterterapia.divermindcenter.dto.*;
import divermindcenterterapia.divermindcenter.entity.*;
import divermindcenterterapia.divermindcenter.exception.*;
import divermindcenterterapia.divermindcenter.repository.*;
import divermindcenterterapia.divermindcenter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // Repositorios requeridos
    private final UserRepository userRepository;
    private final DniRepository dniRepository;
    private final AddressRepository addressRepository;
    private final PhoneRepository phoneRepository;
    private final CommuneRepository communeRepository;
    private final CountryRepository countryRepository;
    private final RehabilitationCenterRepository rehabCenterRepository;

    // Inyección de PasswordEncoder para encriptación de contraseñas
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public UserResponseDTO registerUser(UserRegistrationDTO registrationDTO) {


        // 1. Validar coincidencia de contraseñas
        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            throw new PasswordMismatchException("Las contraseñas no coinciden");
        }

        // 2. Verificar email único
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new DuplicateEmailException("El email ya está registrado");
        }

        // 3. Crear DNI
        Dni dni = dniRepository.save(Dni.builder()
                .value(registrationDTO.getRut())
                .build());

        // 4. Crear Address con comuna
        Commune commune = communeRepository.findById(registrationDTO.getAddress().getCommuneId())
                .orElseThrow(() -> new ResourceNotFoundException("Comuna", "id", registrationDTO.getAddress().getCommuneId()));


        Address address = addressRepository.save(Address.builder()
                .street(registrationDTO.getAddress().getStreet())
                .number(registrationDTO.getAddress().getNumber())
                .references(registrationDTO.getAddress().getReferences())
                .commune(commune)
                .build());

        // 5. Crear User principal
        User user = User.builder()
                .firstName(registrationDTO.getFirstName())
                .email(registrationDTO.getEmail())
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .role(registrationDTO.getRole())
                .dni(dni)
                .address(address)
                .practiceType(registrationDTO.getPracticeType())
                .rehabilitationCenter(getRehabCenter(registrationDTO))
                .build();

        User savedUser = userRepository.save(user);

        // 6. Crear teléfonos
        createPhones(registrationDTO.getPhones(), savedUser);

        return mapToResponseDTO(savedUser);
    }

    // En UserServiceImpl.java
    private RehabilitationCenter getRehabCenter(UserRegistrationDTO dto) {
        if (dto.getPracticeType() == PracticeType.CENTRO_AFILIADO) { // Nombre corregido
            return rehabCenterRepository.findById(dto.getRehabilitationCenterId())
                    .orElseThrow(() -> new ResourceNotFoundException("Centro de rehabilitación", "id", dto.getRehabilitationCenterId()));

        }
        return null;
    }

    private void createPhones(List<PhoneDTO> phonesDTO, User user) {
        if (phonesDTO != null) {
            List<Phone> phones = phonesDTO.stream()
                    .map(phoneDTO -> {
                        Country country = countryRepository.findById(phoneDTO.getCountryId())
                                .orElseThrow(() -> new ResourceNotFoundException("País", "id", phoneDTO.getCountryId()));


                        return Phone.builder()
                                .phoneNumber(phoneDTO.getPhoneNumber())
                                .phoneType(phoneDTO.getPhoneType())
                                .country(country)
                                .user(user)
                                .build();
                    })
                    .collect(Collectors.toList());
            phoneRepository.saveAll(phones);
        }
    }

    private UserResponseDTO mapToResponseDTO(User user) {
        // Formatos para fecha y hora / Date and time formatters
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .email(user.getEmail())
                .role(user.getRole())
                .rut(user.getDni().getValue())
                .practiceType(user.getPracticeType())
                .rehabilitationCenterId(user.getRehabilitationCenter() != null ?
                        user.getRehabilitationCenter().getId() : null)
                .creationDate(user.getCreatedAt().format(dateFormatter)) // Fecha formateada / Formatted date
                .creationTime(user.getCreatedAt().format(timeFormatter)) // Hora formateada / Formatted time
                .build(); // Convierte el builder a UserResponseDTO
    }
    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

        return mapToResponseDTO(user);
    }

    @Override // Indica que esto cumple la promesa del menú
    public UserPageResponseDTO getPagedUsers(int pageNumber, int pageSize) {
        // 1. Validar parámetros básicos
        if (pageNumber < 0) throw new IllegalArgumentException("Número de página no puede ser negativo");
        if (pageSize <= 0 || pageSize > 100) throw new IllegalArgumentException("Tamaño de página debe ser entre 1 y 100");

        // 2. Crear configuración de paginación
        Pageable pageConfig = PageRequest.of(pageNumber, pageSize);

        // 3. Obtener página desde la base de datos
        Page<User> userPage = userRepository.findAll(pageConfig);

        // 4. Convertir usuarios a formato de respuesta
        List<UserResponseDTO> users = userPage.getContent().stream()
                .map(this::mapToResponseDTO)
                .toList();

        // 5. Construir respuesta final
        return UserPageResponseDTO.builder()
                .users(users)
                .currentPage(pageNumber)
                .totalPages(userPage.getTotalPages())
                .totalUsers(userPage.getTotalElements())
                .build();
    }
}