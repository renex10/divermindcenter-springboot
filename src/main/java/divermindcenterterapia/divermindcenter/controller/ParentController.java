package divermindcenterterapia.divermindcenter.controller;

import divermindcenterterapia.divermindcenter.dto.ParentPageResponseDTO;
import divermindcenterterapia.divermindcenter.service.ParentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping
    public ParentPageResponseDTO getParents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return parentService.getPagedParents(page, size);
    }
}