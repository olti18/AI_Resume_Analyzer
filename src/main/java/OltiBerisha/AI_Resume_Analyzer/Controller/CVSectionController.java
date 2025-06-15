package OltiBerisha.AI_Resume_Analyzer.Controller;

import OltiBerisha.AI_Resume_Analyzer.Dto.CVSectionDto;
import OltiBerisha.AI_Resume_Analyzer.Service.CVSectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cv-sections")
@RequiredArgsConstructor
@Tag(name = "CV Sections", description = "Manage and access individual CV sections (e.g. Work Experience, Education, Skills)")
public class CVSectionController {

    private final CVSectionService service;

    @PostMapping("/{cvId}")
    @Operation(summary = "Add a new section to a CV")
    public ResponseEntity<CVSectionDto> save(
            @PathVariable Long cvId,
            @RequestBody CVSectionDto dto) {
        return ResponseEntity.ok(service.save(cvId, dto));
    }

    @GetMapping("/{cvId}")
    @Operation(summary = "Get all sections of a CV for the current user")
    public ResponseEntity<List<CVSectionDto>> getSectionsByCvId(@PathVariable Long cvId) {
        return ResponseEntity.ok(service.getSectionsByCvId(cvId));
    }
}
