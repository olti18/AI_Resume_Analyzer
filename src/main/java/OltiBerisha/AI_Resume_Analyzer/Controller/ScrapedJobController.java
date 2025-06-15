package OltiBerisha.AI_Resume_Analyzer.Controller;

import OltiBerisha.AI_Resume_Analyzer.Dto.ScrapedJobDto;
import OltiBerisha.AI_Resume_Analyzer.Service.ScrapedJobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scraped-jobs")
@RequiredArgsConstructor
@Tag(name = "Scraped Jobs", description = "Manage scraped job listings from external sources")
public class ScrapedJobController {

    private final ScrapedJobService service;

    @GetMapping
    @Operation(summary = "Get all scraped job listings")
    public ResponseEntity<List<ScrapedJobDto>> getAllScrapedJobs() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a specific scraped job by ID")
    public ResponseEntity<ScrapedJobDto> getScrapedJobById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    @Operation(summary = "Save a new scraped job (usually done by scraper)")
    public ResponseEntity<ScrapedJobDto> saveScrapedJob(@RequestBody ScrapedJobDto dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a scraped job by ID")
    public ResponseEntity<Void> deleteScrapedJob(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
