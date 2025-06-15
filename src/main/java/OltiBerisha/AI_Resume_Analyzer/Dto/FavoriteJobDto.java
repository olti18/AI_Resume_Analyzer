package OltiBerisha.AI_Resume_Analyzer.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FavoriteJobDto {
    private Long id;
    private Long jobSuggestionId;
    private LocalDateTime savedAt;
}
