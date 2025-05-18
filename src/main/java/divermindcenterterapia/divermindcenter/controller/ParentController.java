package divermindcenterterapia.divermindcenter.controller;

import divermindcenterterapia.divermindcenter.dto.ParentPageResponseDTO;
import divermindcenterterapia.divermindcenter.dto.ParentRegistrationDTO;
import divermindcenterterapia.divermindcenter.dto.ParentResponseDTO;
import divermindcenterterapia.divermindcenter.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;

    @PostMapping("/register")
    public ResponseEntity<ParentResponseDTO> registerParent(
            @Valid @RequestBody ParentRegistrationDTO registrationDTO) {
        ParentResponseDTO response = parentService.registerParent(registrationDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ParentPageResponseDTO> getParents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(parentService.getPagedParents(page, size));
    }
}