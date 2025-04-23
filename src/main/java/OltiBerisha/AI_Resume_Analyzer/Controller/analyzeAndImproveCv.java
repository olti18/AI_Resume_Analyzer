package OltiBerisha.AI_Resume_Analyzer.Controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.tika.Tika;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.stripe.Stripe.apiKey;

@RestController
public class analyzeAndImproveCv {

    private final String apiKey = "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";
    //private final String apiKey = "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";

    private final RestTemplate restTemplate = new RestTemplate();
    private final Tika tika = new Tika();

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Resource> analyzeAndImproveCv(@RequestParam("file") MultipartFile file) {
        try {
            // 1. Ekstrakto tekstin nga file
            InputStream inputStream = file.getInputStream();
            String extractedText = tika.parseToString(inputStream);

            // 2. Dërgo tekstin tek OpenRouter AI
            String url = "https://openrouter.ai/api/v1/chat/completions";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "thudm/glm-z1-32b:free");

            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(Map.of("role", "system", "content", "You are an HR assistant who analyzes CVs and gives helpful feedback."));
            messages.add(Map.of("role", "user", "content", "Please analyze this CV:\n" + extractedText));
            requestBody.put("messages", messages);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            // 3. Merr përgjigjen nga AI
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            String aiFeedback = (String) ((Map<String, Object>) choices.get(0).get("message")).get("content");

            // 4. Hap dokumentin DOCX dhe shto rekomandimet e AI
            XWPFDocument document = new XWPFDocument(file.getInputStream());

            XWPFParagraph titlePara = document.createParagraph();
            XWPFRun titleRun = titlePara.createRun();
            titleRun.setText("🧠 Rekomandimet e AI për përmirësimin e CV-së:");
            titleRun.setBold(true);
            titleRun.setFontSize(14);

            XWPFParagraph feedbackPara = document.createParagraph();
            XWPFRun feedbackRun = feedbackPara.createRun();
            feedbackRun.setText(aiFeedback);
            feedbackRun.setFontSize(12);

            // 5. Ruaj dokumentin në memorie
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.write(outputStream);
            document.close();

            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

            HttpHeaders downloadHeaders = new HttpHeaders();
            downloadHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CV-e-permiresuar.docx");

            return ResponseEntity.ok()
                    .headers(downloadHeaders)
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }





}
