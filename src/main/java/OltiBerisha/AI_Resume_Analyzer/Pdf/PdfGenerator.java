package OltiBerisha.AI_Resume_Analyzer.Pdf;

import OltiBerisha.AI_Resume_Analyzer.Model.CV;
import OltiBerisha.AI_Resume_Analyzer.Repository.CVRepository;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Entities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.jsoup.nodes.Document;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class PdfGenerator {


    @Autowired
    private CVRepository cvRepository;

    public byte[] generateTemplate1(CV cv) {
        return generatePDF(cv, "template1.html", "template1.css");
    }

    public byte[] generateTemplate2(CV cv) {
        return generatePDF(cv, "template2.html", "css/template2.css");
    }

    public byte[] generateTemplate3(CV cv) {
        return generatePDF(cv, "template3.html", "css/template3.css");
    }

    public byte[] generateDefaultTemplate(CV cv) {
        return generatePDF(cv, "template1.html", "css/template1.css");
    }

    public byte[] generatePDF(Long id, String template) {
        Optional<CV> optionalCv = cvRepository.findById(id);
        if (optionalCv.isEmpty()) {
            throw new RuntimeException("CV not found for ID: " + id);
        }

        CV cv = optionalCv.get();

        return switch (template) {
            case "template1" -> generateTemplate1(cv);
            case "template2" -> generateTemplate2(cv);
            case "template3" -> generateTemplate3(cv);
            default -> generateDefaultTemplate(cv);
        };
    }

    private byte[] generatePDF(CV cv, String htmlFileName, String cssFileName) {
        try {
            InputStream htmlStream = getClass().getClassLoader().getResourceAsStream("templates/" + htmlFileName);
            if (htmlStream == null) throw new RuntimeException("HTML template not found: " + htmlFileName);
            String htmlContent = new String(htmlStream.readAllBytes(), StandardCharsets.UTF_8);

            htmlContent = htmlContent.replace("{{fullname}}", safe(cv.getFullname()))
                    .replace("{{email}}", safe(cv.getEmail()))
                    .replace("{{phone}}", safe(cv.getPhoneNumber()))
                    .replace("{{summary}}", safe(cv.getSummary()))
                    .replace("{{jobTitle}}", safe(cv.getJobTitle()));

            InputStream cssStream = getClass().getClassLoader().getResourceAsStream("templates/" + cssFileName);
            if (cssStream == null) throw new RuntimeException("CSS file not found: " + cssFileName);
            String cssContent = new String(cssStream.readAllBytes(), StandardCharsets.UTF_8);

            htmlContent = htmlContent.replace("{{style}}", "<style>" + cssContent + "</style>");

            Document doc = Jsoup.parse(htmlContent, "UTF-8");
            doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml).escapeMode(Entities.EscapeMode.xhtml);
            String xhtml = doc.html();

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(xhtml);
            renderer.layout();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            renderer.createPDF(outputStream);
            outputStream.close();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF: " + e.getMessage(), e);
        }
    }

    private String safe(String input) {
        return input == null ? "" : input;
    }



}
