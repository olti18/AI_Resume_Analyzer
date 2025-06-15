package OltiBerisha.AI_Resume_Analyzer.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobSuggestionDto {

    private Long id;
    private String jobTitle;
    private String companyName;
    private String location;
    private String url;
    private Double matchScore;
    private LocalDateTime scrapeDate;

}
