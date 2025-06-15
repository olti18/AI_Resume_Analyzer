//package OltiBerisha.AI_Resume_Analyzer.Controller;
//
//import OltiBerisha.AI_Resume_Analyzer.Dto.CVDto;
//import OltiBerisha.AI_Resume_Analyzer.Dto.CVRequestDto;
////import OltiBerisha.AI_Resume_Analyzer.Service.Impl.CvServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.Principal;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/cvs")
//public class CvController {
//
//    @Autowired
//    private CvServiceImpl cvService;
//
//
//
//    @PostMapping
//    public ResponseEntity<CVDto> createCV(@RequestBody CVRequestDto cvRequestDto) {
//        return ResponseEntity.ok(cvService.createCV(cvRequestDto));
//    }
//
////    @GetMapping
////    public ResponseEntity<List<CVDto>> getAllCVs() {
////        return ResponseEntity.ok(cvService.getAllCVs());
////    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<CVDto> getCVById(@PathVariable Long id) {
//        return ResponseEntity.ok(cvService.getCVById(id));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<CVDto> updateCV(@PathVariable Long id, @RequestBody CVRequestDto cvRequestDto) {
//        return ResponseEntity.ok(cvService.updateCV(id, cvRequestDto));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCV(@PathVariable Long id) {
//        cvService.deleteCV(id);
//        return ResponseEntity.noContent().build();
//    }
//
//}


package OltiBerisha.AI_Resume_Analyzer.Controller;


import OltiBerisha.AI_Resume_Analyzer.Config.KeycloakUtils;
import OltiBerisha.AI_Resume_Analyzer.Model.CVAnalysisResult;
import OltiBerisha.AI_Resume_Analyzer.Model.CVSection;
import OltiBerisha.AI_Resume_Analyzer.Model.Cv;
import OltiBerisha.AI_Resume_Analyzer.Repository.CVAnalysisResultRepository;
import OltiBerisha.AI_Resume_Analyzer.Repository.CvRepository;
import OltiBerisha.AI_Resume_Analyzer.Repository.CvSectionRepository;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cv")
public class CvController {

    private final String apiKey = "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";

    private final RestTemplate restTemplate = new RestTemplate();
    private final Tika tika = new Tika();

    @Autowired
    private CvRepository cvRepository;

    @Autowired
    private CVAnalysisResultRepository cvAnalysisResultRepository;

    @Autowired
    private CvSectionRepository cvSectionRepository;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> analyzeCvFile(@RequestParam("file") MultipartFile file) {
        try {
            // 1. Ruaj file në server (mund të ndryshosh path sipas projektit)
            String filePath = saveFileLocally(file);

            // 2. Ekstrakto tekstin
            String extractedText = tika.parseToString(file.getInputStream());
            System.out.println("Extracted text length: " + extractedText.length());
            System.out.println("Extracted text snippet: " + extractedText.substring(0, Math.min(200, extractedText.length())));

            // 3. Ruaj CV-në
            Cv cv = new Cv();
            // Nëse ke autentikim, marrë userId nga security context
            cv.setUserId(KeycloakUtils.getCurrentUserId()); // ndrysho me userin aktual
            cv.setCvFilePath(filePath);
            cv.setTextExtracted(extractedText);
            cv.setUploadDate(LocalDateTime.now());
            cv = cvRepository.save(cv);

            // 4. Thirr AI për analizë
            Map<String, Object> aiResponse = callAiAnalyze(extractedText);

            // 5. Merr rezultatet nga AI (ne supozojmë që AI kthen JSON me summary, improvements dhe sections)
            String summary = (String) aiResponse.getOrDefault("summary", "No summary");
            String suggestedImprovements = (String) aiResponse.getOrDefault("suggestedImprovements", "No suggestions");

            CVAnalysisResult analysisResult = new CVAnalysisResult();
            analysisResult.setCv(cv);
            analysisResult.setSummary(summary);
            analysisResult.setSuggestedImprovements(suggestedImprovements);
            analysisResult.setAnalysisDate(LocalDateTime.now());
            analysisResult.setUserId(cv.getUserId());
            cvAnalysisResultRepository.save(analysisResult);

            // 6. Ruaj seksionet (nëse ka)
            List<Map<String, String>> sections = (List<Map<String, String>>) aiResponse.get("sections");
            if (sections != null) {
                for (Map<String, String> sec : sections) {
                    CVSection section = new CVSection();
                    section.setCvId(cv.getId());
                    section.setSectionName(sec.get("sectionName"));
                    section.setContent(sec.get("content"));
                    cvSectionRepository.save(section);
                }
            }

            // 7. Kthe përgjigjen
            Map<String, Object> result = new HashMap<>();
            result.put("message", "CV uploaded and analyzed successfully.");
            result.put("cvId", cv.getId());
            result.put("analysisResult", Map.of(
                    "summary", summary,
                    "suggestedImprovements", suggestedImprovements
            ));

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gabim gjatë analizimit të CV-së: " + e.getMessage());
        }
    }

    private String saveFileLocally(MultipartFile file) throws Exception {
        // Ruaj file lokal - ndrysho path sipas nevojës
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        java.nio.file.Path path = java.nio.file.Paths.get("uploads/" + fileName);
        java.nio.file.Files.createDirectories(path.getParent());
        java.nio.file.Files.write(path, file.getBytes());
        return path.toString();
    }

    private Map<String, Object> callAiAnalyze(String extractedText) {
        String url = "https://openrouter.ai/api/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "thudm/glm-z1-32b:free");
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of(
                "role", "system",
                "content", "You are an HR assistant. Always respond ONLY with a valid JSON object with two fields: 'summary' and 'suggestedImprovements'. Do not include any extra text, markdown, or explanation."
        ));

        messages.add(Map.of(
                "role", "user",
                "content", "Please analyze this CV and respond with only JSON like this:\n" +
                        "{\n  \"summary\": \"...\",\n  \"suggestedImprovements\": \"...\"\n}\n\n" +
                        "CV Text:\n" + extractedText
        ));
//        messages.add(Map.of("role", "system", "content", "You are an HR assistant who analyzes CVs and gives helpful feedback in JSON format with summary, suggestedImprovements, and sections."));
//        messages.add(Map.of("role", "user", "content", "Please analyze this CV and respond in JSON:\n" + extractedText));
        requestBody.put("messages", messages);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        // Parse JSON response to Map
        // You can use Jackson ObjectMapper here
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            Map<String, Object> fullResponse = mapper.readValue(response.getBody(), Map.class);

            // Në OpenRouter shpesh përgjigja është në choices[0].message.content
            //String content = ((Map)((List)fullResponse.get("choices")).get(0)).get("message").toString();

            Map<String, Object> message = (Map<String, Object>) ((List<?>) fullResponse.get("choices")).get(0);
            Map<String, Object> messageContent = (Map<String, Object>) message.get("message");
            String content = (String) messageContent.get("content");



            // Përpiqu ta parse content si JSON (nëse modeli kthen string JSON)
            // Në rast se nuk kthehet JSON të vërtetë, duhet ta përshtasësh AI promptin që të kthejë JSON
            Map<String, Object> aiData = mapper.readValue(content, Map.class);
            return aiData;

        } catch (Exception e) {
            // Në rast gabimi, kthe përgjigjen si error message në JSON
            return Map.of("summary", "Nuk u mor rezultat i vlefshëm nga AI: " + e.getMessage());
        }
    }
}




















//import OltiBerisha.AI_Resume_Analyzer.Dto.CvDto;
//import OltiBerisha.AI_Resume_Analyzer.Service.CvService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/cvs")
//@RequiredArgsConstructor
//@Tag(name = "CVs", description = "Upload, fetch, and manage CVs")
//public class CvController {
//
//    private final CvService service;
//
//    @PostMapping
//    @Operation(summary = "Upload a new CV")
//    public ResponseEntity<CvDto> uploadCv(@RequestBody CvDto dto) {
//        return ResponseEntity.ok(service.save(dto));
//    }
//
//    @GetMapping
//    @Operation(summary = "Get all CVs for the current user")
//    public ResponseEntity<List<CvDto>> getAllCvs() {
//        return ResponseEntity.ok(service.getAllByCurrentUser());
//    }
//
//    @GetMapping("/{id}")
//    @Operation(summary = "Get a specific CV by ID (only if it belongs to the current user)")
//    public ResponseEntity<CvDto> getCvById(@PathVariable Long id) {
//        return ResponseEntity.ok(service.getById(id));
//    }
//}
