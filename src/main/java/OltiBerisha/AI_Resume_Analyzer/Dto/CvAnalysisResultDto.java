package OltiBerisha.AI_Resume_Analyzer.Dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CvAnalysisResultDto {
    private Long id;
    private String summary;
    private String suggestedImprovments;
    private LocalDateTime analysisDate;
}
