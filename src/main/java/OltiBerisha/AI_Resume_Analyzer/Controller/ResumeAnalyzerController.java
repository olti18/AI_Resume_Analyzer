package OltiBerisha.AI_Resume_Analyzer.Controller;

import okhttp3.*;
import okhttp3.RequestBody;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/cv")
public class ResumeAnalyzerController {

    private static final String API_KEY = "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"; // 🔒 Replace this!
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";

    @PostMapping(value = "/analyze", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> analyzeCV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".pdf")) {
            return ResponseEntity.badRequest().body("Please upload a valid PDF file.");
        }

        try {
            // Step 1: Extract PDF content
            String cvText = extractTextFromPDF(file);

            // Step 2: Prepare DeepSeek prompt
            JSONObject payload = new JSONObject();
            payload.put("model", "deepseek/deepseek-chat:free");

            JSONArray messages = new JSONArray();
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", "Please analyze this CV:\n\n" + cvText);
            messages.put(userMessage);

            payload.put("messages", messages);

            // Step 3: Make HTTP request to DeepSeek
            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(payload.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    return ResponseEntity.ok(response.body().string());
                } else {
                    return ResponseEntity.status(response.code()).body("DeepSeek API error: " + response.message());
                }
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }

    private String extractTextFromPDF(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
}




//package OltiBerisha.AI_Resume_Analyzer.Controller;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.client.RestClient;
//import org.springframework.web.client.HttpClientErrorException;
//
//import java.io.IOException;
//import java.util.*;
//
//@RestController
//@RequestMapping("/api/v1/resume")
//public class ResumeAnalyzerController {
//
//    private static final Logger logger = LoggerFactory.getLogger(ResumeAnalyzerController.class);
//    private static final String DEEPSEEK_API_URL = "https://api.deepseek.com/v1/chat/completions";
//    private final String apiKey = "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";
//    private final RestClient restClient = RestClient.create();
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Map<String, Object>> analyzeResume(
//            @RequestParam("resume") MultipartFile resume,
//            @RequestParam(value = "jobDescription", required = false) String jobDescription) {
//
//        // 1. Input validation
//        if (resume.isEmpty()) {
//            return ResponseEntity.badRequest().body(Map.of(
//                    "error", "Empty file",
//                    "timestamp", System.currentTimeMillis()
//            ));
//        }
//
//        try {
//            // 2. Prepare request
//            String prompt = buildPrompt(jobDescription);
//            Map<String, Object> requestBody = buildRequest(resume, prompt);
//
//            // 3. Execute API call (blocking)
//            Map<String, Object> response = restClient.post()
//                    .uri(DEEPSEEK_API_URL)
//                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body(requestBody)
//                    .retrieve()
//                    .onStatus(HttpStatusCode::isError, (req, res) -> {
//                        throw HttpClientErrorException.create(
//                                res.getStatusCode(),
//                                "DeepSeek API Error",
//                                res.getHeaders(),
//                                res.getBody().toString().getBytes(),
//                                null
//                        );
//                    })
//                    .body(Map.class);
//
//            // 4. Process response
//            return parseResponse(response);
//
//        } catch (HttpClientErrorException e) {
//            logger.error("API Error: {}", e.getMessage());
//            return handleApiError(e);
//        } catch (IOException e) {
//            logger.error("File Processing Error", e);
//            return ResponseEntity.internalServerError().body(Map.of(
//                    "error", "File processing failed",
//                    "details", e.getMessage()
//            ));
//        } catch (Exception e) {
//            logger.error("Unexpected Error", e);
//            return ResponseEntity.internalServerError().body(Map.of(
//                    "error", "Processing failed",
//                    "details", e.getMessage()
//            ));
//        }
//    }
//
//    private String buildPrompt(String jobDescription) {
//        return """
//            Analyze this resume against %s job requirements. Provide JSON feedback:
//            {
//              "summary": "",
//              "strengths": [],
//              "weaknesses": [],
//              "ats_score": 0-100,
//              "keyword_matches": {"missing": [], "found": []},
//              "suggestions": []
//            }
//            """.formatted(jobDescription != null ? "the provided" : "general");
//    }
//
//    private Map<String, Object> buildRequest(MultipartFile file, String prompt) throws IOException {
//        return Map.of(
//                "model", "deepseek/deepseek-chat",
//                "messages", List.of(Map.of(
//                        "role", "user",
//                        "content", prompt,
//                        "attachments", List.of(Map.of(
//                                "file_name", file.getOriginalFilename(),
//                                "file_data", Base64.getEncoder().encodeToString(file.getBytes())
//                        ))
//                )),
//                "temperature", 0.1
//        );
//    }
//
//    private ResponseEntity<Map<String, Object>> parseResponse(Map<String, Object> response) {
//        try {
//            @SuppressWarnings("unchecked")
//            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
//
//            @SuppressWarnings("unchecked")
//            Map<String, Object> message = (Map<String, Object>) choices.getFirst().get("message");
//            String analysisJson = (String) message.get("content");
//
//            @SuppressWarnings("unchecked")
//            Map<String, Object> analysis = objectMapper.readValue(analysisJson, Map.class);
//
//            return ResponseEntity.ok()
//                    .header("X-Analysis-Model", "deepseek-chat")
//                    .body(analysis);
//
//        } catch (ClassCastException | JsonProcessingException e) {
//            throw new RuntimeException("Failed to parse API response", e);
//        }
//    }
//
//    private ResponseEntity<Map<String, Object>> handleApiError(HttpClientErrorException e) {
//        HttpHeaders headers = e.getResponseHeaders() != null ? e.getResponseHeaders() : new HttpHeaders();
//
//        return switch (e.getStatusCode().value()) {
//            case 401 -> ResponseEntity.status(401).body(Map.of(
//                    "error", "Invalid API key",
//                    "solution", "Check DEEPSEEK_API_KEY environment variable"
//            ));
//            case 402 -> ResponseEntity.status(402).body(Map.of(
//                    "error", "API quota exhausted",
//                    "solution", "Upgrade at platform.deepseek.com"
//            ));
//            case 429 -> ResponseEntity.status(429).body(Map.of(
//                    "error", "Rate limit exceeded",
//                    "retry_after", headers.getFirst("Retry-After")
//            ));
//            default -> ResponseEntity.status(502).body(Map.of(
//                    "error", "AI service unavailable",
//                    "status", e.getStatusCode().value(),
//                    "details", e.getResponseBodyAsString()
//            ));
//        };
//    }
//}