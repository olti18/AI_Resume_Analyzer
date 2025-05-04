package OltiBerisha.AI_Resume_Analyzer.Controller;

import OltiBerisha.AI_Resume_Analyzer.Dto.CVDto;
import OltiBerisha.AI_Resume_Analyzer.Dto.CVRequestDto;
import OltiBerisha.AI_Resume_Analyzer.Service.Impl.CvServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/cvs")
public class CvController {
    @Autowired
    private CvServiceImpl cvService;

    @PostMapping
    public ResponseEntity<CVDto> createCV(@RequestBody CVRequestDto cvRequestDto) {
        return ResponseEntity.ok(cvService.createCV(cvRequestDto));
    }

//    @GetMapping
//    public ResponseEntity<List<CVDto>> getAllCVs() {
//        return ResponseEntity.ok(cvService.getAllCVs());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<CVDto> getCVById(@PathVariable Long id) {
        return ResponseEntity.ok(cvService.getCVById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CVDto> updateCV(@PathVariable Long id, @RequestBody CVRequestDto cvRequestDto) {
        return ResponseEntity.ok(cvService.updateCV(id, cvRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCV(@PathVariable Long id) {
        cvService.deleteCV(id);
        return ResponseEntity.noContent().build();
    }

}
