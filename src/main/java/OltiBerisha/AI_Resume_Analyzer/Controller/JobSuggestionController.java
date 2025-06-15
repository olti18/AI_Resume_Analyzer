package OltiBerisha.AI_Resume_Analyzer.Controller;

import OltiBerisha.AI_Resume_Analyzer.Dto.JobSuggestionDto;
import OltiBerisha.AI_Resume_Analyzer.Service.JobSuggestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-suggestions")
@RequiredArgsConstructor
@Tag(name = "Job Suggestions", description = "AI-generated job suggestions based on analyzed CVs")
public class JobSuggestionController {

    private final JobSuggestionService service;

    @GetMapping("/cv/{cvId}")
    @Operation(summary = "Get job suggestions for a specific CV (user must own the CV)")
    public ResponseEntity<List<JobSuggestionDto>> getSuggestionsForCv(@PathVariable Long cvId) {
        return ResponseEntity.ok(service.getSuggestionsForCv(cvId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a specific job suggestion by its ID (user must own the CV)")
    public ResponseEntity<JobSuggestionDto> getSuggestion(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSuggestion(id));
    }
}
