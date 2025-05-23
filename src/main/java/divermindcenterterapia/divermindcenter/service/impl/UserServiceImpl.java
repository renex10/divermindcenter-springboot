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

    private final UserRepository userRepository;
    private final DniRepository dniRepository;
    private final AddressRepository addressRepository;
    private final PhoneRepository phoneRepository;
    private final CommuneRepository communeRepository;
    private final CountryRepository countryRepository;
    private final RehabilitationCenterRepository rehabCenterRepository;
    private final UniversityRepository universityRepository;
    private final UserProfileRepository userProfileRepository;
    private final ProfessionalInfoRepository professionalInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDTO registerUser(UserRegistrationDTO registrationDTO) {
        // Verificación de contraseña
        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            throw new PasswordMismatchException("Las contraseñas no coinciden");
        }

        // Verificación de email único
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new DuplicateEmailException("El email ya está registrado");
        }

        // Creación de DNI
        Dni dni = dniRepository.save(Dni.builder()
                .value(registrationDTO.getRut())
                .build());

        // Creación de dirección
        Commune commune = communeRepository.findById(registrationDTO.getAddress().getCommuneId())
                .orElseThrow(() -> new ResourceNotFoundException("Comuna", "id", registrationDTO.getAddress().getCommuneId()));

        Address address = addressRepository.save(Address.builder()
                .street(registrationDTO.getAddress().getStreet())
                .number(registrationDTO.getAddress().getNumber())
                .references(registrationDTO.getAddress().getReferences())
                .commune(commune)
                .build());

        User user = User.builder()
                .firstName(registrationDTO.getFirstName())
                .email(registrationDTO.getEmail())
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .role(registrationDTO.getRole())
                .dni(dni)
                .address(address)
                .practiceType(registrationDTO.getPracticeType())
                .rehabilitationCenter(getRehabCenter(registrationDTO))
                .university(
                        universityRepository.findById(
                                registrationDTO.getProfile().getUniversityId() // Paréntesis añadido aquí
                        ).orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Universidad",
                                        "id",
                                        registrationDTO.getProfile().getUniversityId()
                                )
                        )
                )
                .active(registrationDTO.isActive()) // <- Añade esta línea
                .build();

        User savedUser = userRepository.save(user);        // Creación de teléfonos
        createPhones(registrationDTO.getPhones(), savedUser);

        // Creación de perfil
        UserProfile profile = UserProfile.builder()
                .lastName(registrationDTO.getProfile().getLastName())
                .birthDate(registrationDTO.getProfile().getBirthDate())
                .gender(registrationDTO.getProfile().getGender())
                .graduationYear(registrationDTO.getProfile().getGraduationYear())
                .yearsExperience(registrationDTO.getProfile().getYearsExperience())
                .university(universityRepository.findById(registrationDTO.getProfile().getUniversityId())
                        .orElseThrow(() -> new ResourceNotFoundException("Universidad", "id", registrationDTO.getProfile().getUniversityId())))
                .user(savedUser)
                .build();
        userProfileRepository.save(profile);

        // Creación de info profesional
        ProfessionalInfo professionalInfo = ProfessionalInfo.builder()
                .experienceSummary(registrationDTO.getProfessionalInfo().getExperienceSummary())
                .biography(registrationDTO.getProfessionalInfo().getBiography()) // Asegúrate de incluir esto
                .serviceModality(registrationDTO.getProfessionalInfo().getServiceModality())
                .userProfile(profile)
                .build();
        professionalInfoRepository.save(professionalInfo);

        return mapToResponseDTO(savedUser);
    }



    private RehabilitationCenter getRehabCenter(UserRegistrationDTO dto) {
        if (dto.getPracticeType() == PracticeType.CENTRO_AFILIADO) {
            // En getRehabCenter:
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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        UserResponseDTO.UserResponseDTOBuilder builder = UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .email(user.getEmail())
                .role(user.getRole())
                .rut(user.getDni().getValue())
                .practiceType(user.getPracticeType())
                .creationDate(user.getCreatedAt().format(dateFormatter))
                .creationTime(user.getCreatedAt().format(timeFormatter))
                .active(user.isActive()); // <- Campo añadido aquí

        if (user.getRehabilitationCenter() != null) {
            builder.rehabilitationCenterId(user.getRehabilitationCenter().getId());
        }

        if (user.getUniversity() != null) {
            builder.universityId(user.getUniversity().getId());
        }

        return builder.build();
    }


    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
        return mapToResponseDTO(user); // Eliminé la línea que modificaba el estado activo
    }

    @Override
    @Transactional
    public UserResponseDTO updateActiveStatus(Long id, boolean active) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

        user.setActive(active);
        User updatedUser = userRepository.save(user);
        return mapToResponseDTO(updatedUser);
    }




    @Override
    public UserPageResponseDTO getPagedUsers(int pageNumber, int pageSize) {
        if (pageNumber < 0) throw new IllegalArgumentException("Número de página inválido");
        if (pageSize <= 0 || pageSize > 100) throw new IllegalArgumentException("Tamaño de página inválido");

        Pageable pageConfig = PageRequest.of(pageNumber, pageSize);
        Page<User> userPage = userRepository.findAll(pageConfig);

        List<UserResponseDTO> users = userPage.getContent().stream()
                .map(this::mapToResponseDTO)
                .toList();

        return UserPageResponseDTO.builder()
                .users(users)
                .currentPage(pageNumber)
                .totalPages(userPage.getTotalPages())
                .totalUsers(userPage.getTotalElements())
                .build();
    }

    private List<PhoneDTO> mapPhonesToDTO(List<Phone> phones) {
        if (phones == null || phones.isEmpty()) {
            return List.of(); // Alternativa moderna a Collections.emptyList()
        }

        return phones.stream()
                .map(phone -> {
                    PhoneDTO dto = new PhoneDTO();
                    dto.setId(phone.getId());
                    dto.setPhoneNumber(phone.getPhoneNumber());
                    dto.setPhoneType(phone.getPhoneType());
                    dto.setCountryId(phone.getCountry() != null ? phone.getCountry().getId() : null);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    @Override
    public UserProfileResponseDTO getUserProfileWithDetails(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", userId));

        UserProfile profile = user.getProfile();
        ProfessionalInfo professionalInfo = profile.getProfessionalInfo(); // Ahora debería funcionar

        return UserProfileResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .email(user.getEmail())
                .rut(user.getDni().getValue())
                .lastName(profile.getLastName())
                .birthDate(profile.getBirthDate().toString())
                .gender(profile.getGender().name())
                .universityName(profile.getUniversity().getName())
                .biography(professionalInfo.getBiography())
                .experienceSummary(professionalInfo.getExperienceSummary())
                .serviceModality(professionalInfo.getServiceModality().name())
                .phones(mapPhonesToDTO(user.getPhones())) // Método añadido
                .build();
    }
}