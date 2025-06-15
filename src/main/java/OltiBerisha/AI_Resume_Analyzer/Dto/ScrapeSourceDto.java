package OltiBerisha.AI_Resume_Analyzer.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScrapeSourceDto {
    private Long id;
    private String name;
    private String baseUrl;
    private boolean active;
    private LocalDateTime lastScrapeTime;
}
