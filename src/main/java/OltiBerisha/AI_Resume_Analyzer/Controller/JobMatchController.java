package OltiBerisha.AI_Resume_Analyzer.Controller;

import okhttp3.*;
import okhttp3.RequestBody;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/jobs")
public class JobMatchController {

    private static final String API_KEY = "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"; // 🔒 Replace me!
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final OkHttpClient client = new OkHttpClient();

    @PostMapping(value = "/match", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> matchJobs(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty() || !file.getOriginalFilename().endsWith(".pdf")) {
                return ResponseEntity.badRequest().body("Please upload a valid PDF CV.");
            }

            // Step 1: Extract CV text
            String cvText = extractTextFromPDF(file);

            // Step 2: Analyze CV
            String analyzedCV = askDeepSeek("Analyze this CV:\n" + cvText);

            // Step 3: Scrape job links from Kerkopune
            List<String> jobLinks = fetchJobLinks();

            // Step 4: Match jobs
            List<Map<String, String>> matchedJobs = new ArrayList<>();

            for (String jobUrl : jobLinks) {
                String jobText = getJobDetails(jobUrl);
                String prompt = "Given this CV analysis: \n" + analyzedCV +
                        "\n\nAnd this job posting:\n" + jobText +
                        "\n\nIs this a good match? Answer with Yes or No and explain shortly.";

                String matchResult = askDeepSeek(prompt);

                if (matchResult.toLowerCase().contains("yes")) {
                    Map<String, String> match = new HashMap<>();
                    match.put("url", jobUrl);
                    match.put("reason", matchResult);
                    matchedJobs.add(match);
                }
            }

            return ResponseEntity.ok(matchedJobs);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }

    // Helper: Extract text from uploaded PDF
    private String extractTextFromPDF(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            return new PDFTextStripper().getText(document);
        }
    }

    // Helper: Scrape job listing URLs
    private List<String> fetchJobLinks() throws IOException {
        List<String> links = new ArrayList<>();
        Document doc = Jsoup.connect("https://kosovajob.com").get();

        //Elements jobBoxes = doc.select("a.job-box");
        // kontrollo klasën HTML në faqe
        Elements jobBoxes = doc.select("jobListCnts");
        for (Element job : jobBoxes) {
            String url = job.absUrl("href");
            if (!url.isEmpty()) links.add(url);
        }

        return links;
    }

    // Helper: Get job details by URL
    private String getJobDetails(String jobUrl) throws IOException {
        Document doc = Jsoup.connect(jobUrl).get();
        return doc.text(); // ose doc.select("div.job-description").text();
    }

    // Helper: Call DeepSeek API
    private String askDeepSeek(String prompt) throws IOException {
        JSONObject payload = new JSONObject();
        payload.put("model", "deepseek/deepseek-chat:free");

        JSONArray messages = new JSONArray();
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messages.put(userMessage);

        payload.put("messages", messages);

        RequestBody body = RequestBody.create(payload.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body() != null ? response.body().string() : "No response";
        }
    }
}

