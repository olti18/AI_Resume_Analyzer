package OltiBerisha.AI_Resume_Analyzer.Controller;

import org.apache.tika.Tika;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cv")
public class CvAnalyzerController {

    private final String apiKey = "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";

    private final RestTemplate restTemplate = new RestTemplate();
    private final Tika tika = new Tika();

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> analyzeCvFile(@RequestParam("file") MultipartFile file) {
        try {
            // 1. Ekstrakto tekstin nga file (PDF, DOCX, etj.)
            InputStream inputStream = file.getInputStream();
            String extractedText = tika.parseToString(inputStream);

            // 2. Përgatit kërkesën për OpenRouter
            String url = "https://openrouter.ai/api/v1/chat/completions";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "thudm/glm-z1-32b:free");
            //requestBody.put("model", "openai/gpt-4o");
            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(Map.of("role", "system", "content", "You are an HR assistant who analyzes CVs and gives helpful feedback."));
            messages.add(Map.of("role", "user", "content", "Please analyze this CV:\n" + extractedText));
            requestBody.put("messages", messages);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gabim gjatë analizimit të CV-së: " + e.getMessage());
        }
    }


//    private String apiKey ="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";
//
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> analyzeCv(@RequestBody String cvText) {
//        try {
//            String url = "https://openrouter.ai/api/v1/chat/completions";
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.set("Authorization", "Bearer " + apiKey);
//
//            // Request body format for OpenRouter (like OpenAI Chat)
//            Map<String, Object> requestBody = new HashMap<>();
//            requestBody.put("model", "mistralai/mixtral-8x7b"); // or "meta-llama/llama-3-8b-instruct"
//            List<Map<String, String>> messages = new ArrayList<>();
//            messages.add(Map.of("role", "system", "content", "You are a professional HR assistant who analyzes CVs and gives structured feedback."));
//            messages.add(Map.of("role", "user", "content", "Analyze this CV:\n" + cvText));
//            requestBody.put("messages", messages);
//
//            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
//
//            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
//
//            return ResponseEntity.ok(response.getBody());
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Gabim gjatë analizimit të CV-së: " + e.getMessage());
//        }
//    }

}
