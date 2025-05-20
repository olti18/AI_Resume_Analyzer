package OltiBerisha.AI_Resume_Analyzer.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import OltiBerisha.AI_Resume_Analyzer.Config.KeycloakUtils;
import OltiBerisha.AI_Resume_Analyzer.Dto.CVDto;
import OltiBerisha.AI_Resume_Analyzer.Mapper.CvMapper;
import OltiBerisha.AI_Resume_Analyzer.Model.CV;
import OltiBerisha.AI_Resume_Analyzer.Pdf.PdfGenerator;
import OltiBerisha.AI_Resume_Analyzer.Repository.CVRepository;
import OltiBerisha.AI_Resume_Analyzer.Service.CvService;
import OltiBerisha.AI_Resume_Analyzer.Service.Impl.CvServiceImpl;
import OltiBerisha.AI_Resume_Analyzer.Service.Impl.PdfGeneratorService;
import OltiBerisha.AI_Resume_Analyzer.Service.Impl.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static OltiBerisha.AI_Resume_Analyzer.Config.KeycloakUtils.getCurrentUserId;

@RestController
public class PdfController {
    private static final Logger log = LoggerFactory.getLogger(PdfController.class);

    @Autowired
    private CvMapper CvMapper;

    @Autowired
    private CvService cvService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @Autowired
    private PdfService pdfService; // Correct this




    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id, @RequestParam String template) {
        try {
            byte[] pdfBytes = pdfService.generatePdf(id, template);
            if (pdfBytes == null || pdfBytes.length == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename("cv_" + id + ".pdf")
                    .build());

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error generating PDF for CV id {} with template {}: {}", id, template, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }





//    @GetMapping("/download/{cvId}")
//    public ResponseEntity<byte[]> downloadPdf(
//            @PathVariable Long cvId,
//            @RequestParam(defaultValue = "default") String template) {
//        try {
//            CVDto cvDto = cvService.findCvById(cvId);
//            String currentUserId = getCurrentUserId();
//            if (!cvDto.getUserId().equals(currentUserId)) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//            }
//
//            CV cv = CvMapper.toEntity(cvDto); // Now this works
//            // Corrected here
//
//            byte[] pdfBytes = switch (template.toLowerCase()) {
//                case "template1" -> pdfService.generateTemplate1(cv);
//                case "template2" -> pdfService.generateTemplate2(cv);
//                case "template3" -> pdfService.generateTemplate3(cv);
//                default -> pdfService.generateDefaultTemplate(cv);
//            };
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_PDF);
//            headers.setContentDisposition(ContentDisposition
//                    .attachment()
//                    .filename("cv_" + cvId + ".pdf")
//                    .build());
//
//            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(null);
//        }
//    }



//    @GetMapping("/cv/{id}/download")
//    public ResponseEntity<byte[]> generateAndDownloadPdf(@PathVariable Long id) {
//        CVDto cv = cvService.findCvById(id);
//
//
//        String currentUserId = getCurrentUserId();
//        if (!cv.getUserId().equals(getCurrentUserId())) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        byte[] pdfBytes = pdfGeneratorService.generateCvPdf(cv);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cv-" + id + ".pdf")
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(pdfBytes);
//    }




}
