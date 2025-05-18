package divermindcenterterapia.divermindcenter.service.impl;

import divermindcenterterapia.divermindcenter.dto.*;
import divermindcenterterapia.divermindcenter.entity.*;
import divermindcenterterapia.divermindcenter.exception.*;
import divermindcenterterapia.divermindcenter.repository.*;
import divermindcenterterapia.divermindcenter.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final DniRepository dniRepository;
    private final AddressRepository addressRepository;
    private final CommuneRepository communeRepository;
    private final CountryRepository countryRepository;
    private final PhoneRepository phoneRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ParentPageResponseDTO getPagedParents(int pageNumber, int pageSize) {
        Page<Parent> parentPage = parentRepository.findAll(PageRequest.of(pageNumber, pageSize));

        return ParentPageResponseDTO.builder()
                .parents(parentPage.getContent().stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()))
                .currentPage(pageNumber)
                .totalPages(parentPage.getTotalPages())
                .totalParents(parentPage.getTotalElements())
                .build();
    }

    @Override
    @Transactional
    public ParentResponseDTO registerParent(ParentRegistrationDTO registrationDTO) {
        // Validaciones
        if (dniRepository.existsByValue(registrationDTO.getRut())) {
            throw new DuplicateRutException("El RUT ya está registrado");
        }

        if (parentRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new DuplicateEmailException("El email ya está en uso");
        }

        // Crear DNI
        Dni dni = dniRepository.save(Dni.builder()
                .value(registrationDTO.getRut())
                .build());

        // Crear dirección
        Address address = createAddress(registrationDTO.getAddress());

        // Crear padre
        Parent parent = Parent.builder()
                .firstName(registrationDTO.getFirstName())
                .lastName(registrationDTO.getLastName())
                .email(registrationDTO.getEmail())
                .passwordHash(passwordEncoder.encode(registrationDTO.getPassword()))
                .dni(dni)
                .address(address)
                .gender(Gender.valueOf(registrationDTO.getGender().toUpperCase())) // Conversión a Enum
                .nationality(countryRepository.findById(registrationDTO.getCountryId())
                        .orElseThrow(() -> new ResourceNotFoundException("País", "id", registrationDTO.getCountryId())))
                .registeredBy(userRepository.findById(registrationDTO.getRegisteredByUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", registrationDTO.getRegisteredByUserId())))
                .build();

        Parent savedParent = parentRepository.save(parent);

        // Crear teléfonos
        createPhones(registrationDTO.getPhones(), savedParent);

        return convertToDTO(savedParent);
    }

    private Address createAddress(ParentRegistrationDTO.AddressRegistrationDTO addressDTO) {
        Commune commune = communeRepository.findById(addressDTO.getCommuneId())
                .orElseThrow(() -> new ResourceNotFoundException("Comuna", "id", addressDTO.getCommuneId()));

        return addressRepository.save(Address.builder()
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .references(addressDTO.getReferences())
                .commune(commune)
                .build());
    }

    // En ParentServiceImpl.java
    private void createPhones(List<ParentRegistrationDTO.PhoneDTO> phonesDTO, Parent parent) {
        List<Phone> phones = phonesDTO.stream().map(phoneDTO -> {
            Country country = countryRepository.findById(phoneDTO.getCountryId())
                    .orElseThrow(() -> new ResourceNotFoundException("País", "id", phoneDTO.getCountryId()));

            Phone phone = Phone.builder()
                    .phoneNumber(phoneDTO.getPhoneNumber())
                    .phoneType(phoneDTO.getPhoneType())
                    .country(country)
                    .parent(parent)
                    .build();

            parent.getPhones().add(phone); // Añadir el teléfono a la lista del Parent
            return phone;
        }).collect(Collectors.toList());

        phoneRepository.saveAll(phones);
    }

    private ParentResponseDTO convertToDTO(Parent parent) {
        return ParentResponseDTO.builder()
                .id(parent.getParentId())
                .fullName(parent.getFirstName() + " " + parent.getLastName())
                .email(parent.getEmail())
                .rut(parent.getDni().getValue())
                .createdAt(formatDateTime(parent.getCreatedAt()))
                .isActive(parent.getIsActive() != null ? parent.getIsActive() : true) // Manejo de null
                .phones(getFormattedPhones(parent))
                .build();
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    private List<String> getFormattedPhones(Parent parent) {
        return parent.getPhones().stream()
                .map(phone -> "+" + phone.getCountry().getPhoneCode() + " " + phone.getPhoneNumber())
                .collect(Collectors.toList());
    }
}