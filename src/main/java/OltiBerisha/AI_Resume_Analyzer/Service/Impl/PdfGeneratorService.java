package OltiBerisha.AI_Resume_Analyzer.Service.Impl;

import OltiBerisha.AI_Resume_Analyzer.Dto.CVDto;
import OltiBerisha.AI_Resume_Analyzer.Mapper.CvMapper;
import OltiBerisha.AI_Resume_Analyzer.Model.CV;
import OltiBerisha.AI_Resume_Analyzer.Repository.CVRepository;
import OltiBerisha.AI_Resume_Analyzer.Service.CvService;
import OltiBerisha.AI_Resume_Analyzer.Service.PdfGeneration;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfGeneratorService implements CvService {


    @Autowired
    private CVRepository cvRepository;

    @Autowired
    private CvMapper cvMapper;


    @Override
    public CVDto findCvById(Long id) {
        CV cv = cvRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CV not found with id " + id));
        return CvMapper.toDto(cv);
    }

    public byte[] generateCvPdf(CVDto cv) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            document.add(new Paragraph("CV - " + cv.getFullname(), titleFont));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Email: " + cv.getEmail(), normalFont));
            document.add(new Paragraph("Phone: " + cv.getPhoneNumber(), normalFont));
            document.add(new Paragraph("Job Title: " + cv.getJobTitle(), normalFont));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Summary:", titleFont));
            document.add(new Paragraph(cv.getSummary(), normalFont));
            document.add(Chunk.NEWLINE);

            if (cv.getExperiences() != null && !cv.getExperiences().isEmpty()) {
                document.add(new Paragraph("Experiences:", titleFont));
                for (var exp : cv.getExperiences()) {
                    document.add(new Paragraph("- " + exp.getJobTitle() + " at " + exp.getCompanyName(), normalFont));
                }
            }

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

}
