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
    import java.util.*;

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
                //String summary = (String) aiResponse.getOrDefault("summary", "No summary");
                //String suggestedImprovements = (String) aiResponse.getOrDefault("suggestedImprovements", "No suggestions");

                //String summary = String.valueOf(aiResponse.getOrDefault("summary", "No summary"));
                //String suggestedImprovements = String.valueOf(aiResponse.getOrDefault("suggestedImprovements", "No suggestions"));

                Object summaryObj = aiResponse.get("summary");
                Object improvementsObj = aiResponse.get("suggestedImprovements");

                String summary = summaryObj instanceof String ? (String) summaryObj : "No valid summary";
                String suggestedImprovements = improvementsObj instanceof String ? (String) improvementsObj : "No valid improvements";
                // this is new
//                List<String> skills = (List<String>) aiResponse.getOrDefault("skills", new ArrayList<>());
//                List<String> jobTitles = (List<String>) aiResponse.getOrDefault("jobTitles", new ArrayList<>());
//                String experienceLevel = (String) aiResponse.getOrDefault("experienceLevel", "unknown");

                System.out.println("Summary: " + summary);
                System.out.println("Suggested Improvements: " + suggestedImprovements);


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
//            messages.add(Map.of(
//                    "role", "system",
//                    "content", "You are an HR assistant. Always respond ONLY with a valid JSON object with two fields: 'summary' and 'suggestedImprovements'. Do not include any extra text, markdown, or explanation."
//            ));
//
//            messages.add(Map.of(
//                    "role", "user",
//                    "content", "Please analyze this CV and respond with only JSON like this:\n" +
//                            "{\n  \"summary\": \"...\",\n  \"suggestedImprovements\": \"...\"\n}\n\n" +
//                            "CV Text:\n" + extractedText
//            ));

            messages.add(Map.of(
                    "role", "system",
                    "content", "You are an HR assistant. Always respond ONLY with a valid JSON object with the following fields: 'summary', 'suggestedImprovements', 'skills' (as list), 'jobTitles' (as list), and 'experienceLevel'. Respond only with JSON."
            ));

            messages.add(Map.of(
                    "role", "user",
                    "content", "Analyze this CV and return JSON like this:\n" +
                            "{\n" +
                            "  \"summary\": \"...\",\n" +
                            "  \"suggestedImprovements\": \"...\",\n" +
                            "  \"skills\": [\"Java\", \"Spring\", \"Docker\"],\n" +
                            "  \"jobTitles\": [\"Backend Developer\", \"Software Engineer\"],\n" +
                            "  \"experienceLevel\": \"mid\"\n" +
                            "}\n\n" +
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




        private Map<String, Object> callAiJobRecommendation(String extractedText) {
            String url = "https://openrouter.ai/api/v1/chat/completions";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "thudm/glm-z1-32b:free");

            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(Map.of(
                    "role", "system",
                    "content", "You are an AI assistant that extracts job-matching data from CVs. Respond ONLY in valid JSON like:\n" +
                            "{ \"skills\": [\"Java\", \"Spring\"], \"jobTitles\": [\"Backend Developer\"], \"experienceLevel\": \"mid\" }"
            ));
            messages.add(Map.of(
                    "role", "user",
                    "content", "Here is the CV text:\n" + extractedText
            ));

            requestBody.put("messages", messages);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            try {
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                Map<String, Object> fullResponse = mapper.readValue(response.getBody(), Map.class);

                Map<String, Object> message = (Map<String, Object>) ((List<?>) fullResponse.get("choices")).get(0);
                Map<String, Object> messageContent = (Map<String, Object>) message.get("message");
                String content = (String) messageContent.get("content");

                Map<String, Object> aiData = mapper.readValue(content, Map.class);
                return aiData;

            } catch (Exception e) {
                return Map.of("error", "AI parsing failed: " + e.getMessage());
            }
        }


        @PostMapping("/recommendations")
        public ResponseEntity<?> getJobRecommendations(@RequestParam("cvId") Long cvId) {
            try {
                Optional<Cv> cvOpt = cvRepository.findById(cvId);
                if (cvOpt.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CV not found for ID: " + cvId);
                }

                Cv cv = cvOpt.get();
                String extractedText = cv.getTextExtracted();

                // Thirr AI përsëri për të marrë rekomandimet
                Map<String, Object> aiResponse = callAiJobRecommendation(extractedText); // funksion i ri që duhet ta shtosh

                // Përgjigja e AI duhet të jetë një JSON me: skills, jobTitles, experienceLevel
                List<String> skills = (List<String>) aiResponse.getOrDefault("skills", new ArrayList<>());
                List<String> jobTitles = (List<String>) aiResponse.getOrDefault("jobTitles", new ArrayList<>());
                String experienceLevel = (String) aiResponse.getOrDefault("experienceLevel", "unknown");

                // Përgatit payload për Flask
                Map<String, Object> requestPayload = new HashMap<>();
                requestPayload.put("skills", skills);
                requestPayload.put("experienceLevel", experienceLevel);
                requestPayload.put("jobTitles", jobTitles);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestPayload, headers);

                // Dërgo POST request tek Flask
                //String flaskUrl = "http://localhost:5000/match-jobs";
                String flaskUrl = "http://127.0.0.1:5000/match-jobs";

                ResponseEntity<String> flaskResponse = restTemplate.postForEntity(flaskUrl, request, String.class);

                return ResponseEntity.ok(flaskResponse.getBody());

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error generating job recommendations: " + e.getMessage());
            }
        }
    





    }