package OltiBerisha.AI_Resume_Analyzer.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnalysisLogDto {
    private Long id;
    private Long cvId;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String errorMessage;
}

