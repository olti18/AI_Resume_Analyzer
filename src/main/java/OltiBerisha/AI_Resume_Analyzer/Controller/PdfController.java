package OltiBerisha.AI_Resume_Analyzer.Controller;

import OltiBerisha.AI_Resume_Analyzer.Config.KeycloakUtils;
import OltiBerisha.AI_Resume_Analyzer.Dto.CVDto;
import OltiBerisha.AI_Resume_Analyzer.Repository.CVRepository;
import OltiBerisha.AI_Resume_Analyzer.Service.CvService;
import OltiBerisha.AI_Resume_Analyzer.Service.Impl.CvServiceImpl;
import OltiBerisha.AI_Resume_Analyzer.Service.Impl.PdfGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static OltiBerisha.AI_Resume_Analyzer.Config.KeycloakUtils.getCurrentUserId;

@RestController
public class PdfController {


    @Autowired
    private CvService cvService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;


    @GetMapping("/cv/{id}/download")
    public ResponseEntity<byte[]> generateAndDownloadPdf(@PathVariable Long id) {
        CVDto cv = cvService.findCvById(id);


        String currentUserId = getCurrentUserId();
        if (!cv.getUserId().equals(getCurrentUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        byte[] pdfBytes = pdfGeneratorService.generateCvPdf(cv);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cv-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }




}
