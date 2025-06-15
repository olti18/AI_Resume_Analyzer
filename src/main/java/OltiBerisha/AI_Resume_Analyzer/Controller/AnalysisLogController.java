package OltiBerisha.AI_Resume_Analyzer.Controller;

import OltiBerisha.AI_Resume_Analyzer.Dto.AnalysisLogDto;
import OltiBerisha.AI_Resume_Analyzer.Service.AnalysisLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analysis-logs")
@RequiredArgsConstructor
@Tag(name = "Analysis Logs", description = "Track CV analysis process logs")
public class AnalysisLogController {

    private final AnalysisLogService service;

    @GetMapping("/{cvId}")
    @Operation(summary = "Get all logs for a specific CV")
    public ResponseEntity<List<AnalysisLogDto>> getByCvId(@PathVariable Long cvId) {
        return ResponseEntity.ok(service.getByCvId(cvId));
    }

    @PostMapping
    @Operation(summary = "Create a new analysis log entry")
    public ResponseEntity<AnalysisLogDto> createLog(@RequestBody AnalysisLogDto dto) {
        AnalysisLogDto saved = service.save(dto);
        return ResponseEntity.ok(saved);
    }
}


