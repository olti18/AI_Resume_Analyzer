package OltiBerisha.AI_Resume_Analyzer.Controller;

import OltiBerisha.AI_Resume_Analyzer.Dto.CvAnalysisResultDto;
import OltiBerisha.AI_Resume_Analyzer.Model.Cv;
import OltiBerisha.AI_Resume_Analyzer.Service.CVAnalysisResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cv-analysis-results")
@RequiredArgsConstructor
@Tag(name = "CV Analysis Results", description = "Manage and fetch AI analysis results of CVs")
public class CVAnalysisResultController {

    private final CVAnalysisResultService service;

    @PostMapping("/{cvId}")
    @Operation(summary = "Analyze and save a new CV analysis result")
    public ResponseEntity<CvAnalysisResultDto> save(
            @PathVariable Cv cvId,
            @RequestBody CvAnalysisResultDto dto) {
        return ResponseEntity.ok(service.save(cvId, dto));
    }

    @GetMapping("/{cvId}")
    @Operation(summary = "Get analysis results for a CV belonging to the current user")
    public ResponseEntity<List<CvAnalysisResultDto>> getByCvId(@PathVariable Long cvId) {
        return ResponseEntity.ok(service.getByCvId(cvId));
    }
}
