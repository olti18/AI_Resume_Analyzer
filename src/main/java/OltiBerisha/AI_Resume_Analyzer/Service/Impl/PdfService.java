package OltiBerisha.AI_Resume_Analyzer.Service.Impl;

import OltiBerisha.AI_Resume_Analyzer.Pdf.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PdfService {

    @Autowired
    private PdfGenerator pdfGenerator;

    public byte[] generatePdf(Long id, String template) {
        return pdfGenerator.generatePDF(id, template);
    }

}
