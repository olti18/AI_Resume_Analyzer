package OltiBerisha.AI_Resume_Analyzer.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CVDto {
    private Long id;
    private String userId;
    private String fullname;
    private String email;
    private String phoneNumber;
    private String jobTitle;
    private String summary;
    private List<ExperienceDto> experiences;
}
