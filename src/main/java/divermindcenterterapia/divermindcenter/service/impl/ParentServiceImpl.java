package divermindcenterterapia.divermindcenter.service.impl;

import divermindcenterterapia.divermindcenter.dto.ParentPageResponseDTO;
import divermindcenterterapia.divermindcenter.dto.ParentResponseDTO;
import divermindcenterterapia.divermindcenter.entity.Parent;
import divermindcenterterapia.divermindcenter.repository.ParentRepository;
import divermindcenterterapia.divermindcenter.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;

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

    private ParentResponseDTO convertToDTO(Parent parent) {
        return ParentResponseDTO.builder()
                .id(parent.getParentId())
                .fullName(parent.getFirstName() + " " + parent.getLastName())
                .email(parent.getEmail())
                .createdAt(formatDateTime(parent.getCreatedAt()))
                .isActive(parent.getIsActive())
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