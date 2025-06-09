//package OltiBerisha.AI_Resume_Analyzer.Service.Impl;
//
//import lombok.Value;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class ChatGptServiceImpl {
//
//    private String apiKey = "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"; // Replace with real key
//    private static final String OPENROUTER_URL = "https://openrouter.ai/api/v1/chat/completions";
//
//    public String analyzeCvWithGpt(String cvText) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + apiKey);
//        headers.set("HTTP-Referer", "http://localhost:8080"); // Or your app URL
//        headers.set("X-Title", "AI Resume Analyzer");
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        Map<String, Object> requestBody = Map.of(
//                "model", "openrouter/mixtral-8x7b", // Or another OpenRouter-supported model
//                "messages", List.of(
//                        Map.of("role", "system", "content", "You are a CV analyzer AI."),
//                        Map.of("role", "user", "content", generatePrompt(cvText))
//                )
//        );
//
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
//
//        try {
//            ResponseEntity<Map> response = restTemplate.exchange(
//                    OPENROUTER_URL,
//                    HttpMethod.POST,
//                    entity,
//                    Map.class
//            );
//
//            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
//            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
//            return message.get("content").toString();
//
//        } catch (Exception e) {
//            return "Error contacting OpenRouter: " + e.getMessage();
//        }
//    }
//
//    private String generatePrompt(String cvText) {
//        return """
//            Analyze this CV. Return a JSON with:
//            - skills: [string]
//            - recommendedJobs: [string]
//            - feedback: string
//
//            CV Text:
//            """ + cvText;
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////package OltiBerisha.AI_Resume_Analyzer.Service.Impl;
////
////import lombok.Value;
////import org.springframework.http.HttpHeaders;
////import org.springframework.http.HttpEntity;
////import org.springframework.http.HttpMethod;
////import org.springframework.http.MediaType;
////import org.springframework.http.ResponseEntity;
////import org.springframework.stereotype.Service;
////import org.springframework.web.client.RestTemplate;
////
////import java.util.List;
////import java.util.Map;
////
////@Service
////public class ChatGptServiceImpl {
////
////    //@Value("${openai.api.key}")
////    private String apiKey ="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";
////
////    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";
////
////    public String analyzeCvWithGpt(String cvText) {
////        RestTemplate restTemplate = new RestTemplate();
////
////        HttpHeaders headers = new HttpHeaders();
////        headers.setBearerAuth(apiKey); // ✅ this works with Spring's HttpHeaders
////        headers.setContentType(MediaType.APPLICATION_JSON);
////
////        Map<String, Object> requestBody = Map.of(
////                "model", "gpt-3.5-turbo",
////                "messages", List.of(
////                        Map.of("role", "system", "content", "You are a CV analyzer AI."),
////                        Map.of("role", "user", "content", generatePrompt(cvText))
////                )
////        );
////
////        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
////
////        ResponseEntity<Map> response = restTemplate.exchange(
////                OPENAI_URL,
////                HttpMethod.POST,
////                entity,
////                Map.class
////        );
////
////        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
////        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
////        return message.get("content").toString();
////    }
////
////    private String generatePrompt(String cvText) {
////        return """
////            Analyze this CV. Return a JSON with:
////            - skills: [string]
////            - recommendedJobs: [string]
////            - feedback: string
////
////            CV Text:
////            """ + cvText;
////    }
////}
