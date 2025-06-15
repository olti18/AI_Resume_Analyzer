package OltiBerisha.AI_Resume_Analyzer.Controller;

import OltiBerisha.AI_Resume_Analyzer.Dto.FavoriteJobDto;
import OltiBerisha.AI_Resume_Analyzer.Service.FavoriteJobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@Tag(name = "Favorite Jobs", description = "Manage favorite job suggestions")
public class FavoriteJobController {

    private final FavoriteJobService favoriteJobService;

    @GetMapping
    @Operation(summary = "Get all favorite jobs of the current user")
    public ResponseEntity<List<FavoriteJobDto>> getFavorites() {
        return ResponseEntity.ok(favoriteJobService.getAllForCurrentUser());
    }

    @PostMapping("/{jobSuggestionId}")
    @Operation(summary = "Add a job suggestion to favorites")
    public ResponseEntity<FavoriteJobDto> addFavorite(@PathVariable Long jobSuggestionId) {
        return ResponseEntity.ok(favoriteJobService.save(jobSuggestionId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove a job from favorites")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long id) {
        favoriteJobService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

