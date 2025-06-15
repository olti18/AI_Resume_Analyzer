//package OltiBerisha.AI_Resume_Analyzer.Controller;
//
//import OltiBerisha.AI_Resume_Analyzer.Dto.ScrapeSourceDto;
//import OltiBerisha.AI_Resume_Analyzer.Service.ScrapeSourceService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/scrape-sources")
//@RequiredArgsConstructor
//@Tag(name = "Scrape Sources", description = "Manage scraping sources (admin-only)")
//public class ScrapeSourceController {
//
//    private final ScrapeSourceService service;
//
//    @GetMapping
//    @Operation(summary = "Get all sources")
//    public ResponseEntity<List<ScrapeSourceDto>> getAll() {
//        return ResponseEntity.ok(service.getAll());
//    }
//
//    @PostMapping
//    @Operation(summary = "Add a new source")
//    public ResponseEntity<ScrapeSourceDto> create(@RequestBody ScrapeSourceDto dto) {
//        return ResponseEntity.ok(service.save(dto));
//    }
//
////    @PatchMapping("/{id}/disable")
////    @Operation(summary = "Disable a source")
////    public ResponseEntity<Void> disable(@PathVariable Long id) {
////        service.disable(id);
////        return ResponseEntity.noContent().build();
////    }
//}
