package OltiBerisha.AI_Resume_Analyzer.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScrapedJobDto {
    private Long id;
    private String source;
    private String jobTitle;
    private String company;
    private String location;
    private String description;
    private String jobUrl;
    private LocalDateTime scrapeDate;
}

