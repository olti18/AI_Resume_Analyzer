package OltiBerisha.AI_Resume_Analyzer.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CvDto {
    private Long id;
    private String cvFilePath;
    private String textExtracted;
    private LocalDateTime uploadDate;
}
