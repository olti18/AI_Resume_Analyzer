package OltiBerisha.AI_Resume_Analyzer.Mapper;

import OltiBerisha.AI_Resume_Analyzer.Dto.CVDto;
import OltiBerisha.AI_Resume_Analyzer.Dto.CVRequestDto;
import OltiBerisha.AI_Resume_Analyzer.Dto.ExperienceDto;
import OltiBerisha.AI_Resume_Analyzer.Dto.ExperienceRequestDto;
import OltiBerisha.AI_Resume_Analyzer.Model.CV;
import OltiBerisha.AI_Resume_Analyzer.Model.Experience;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CvMapper {

    // CVRequestDto -> Entity
    public static CV toEntity(CVRequestDto dto) {
        CV cv = new CV();
        cv.setUserId(dto.getUserId());
        cv.setFullname(dto.getFullname());
        cv.setEmail(dto.getEmail());
        cv.setPhoneNumber(dto.getPhoneNumber());
        cv.setJobTitle(dto.getJobTitle());
        cv.setSummary(dto.getSummary());

        if (dto.getExperiences() != null) {
            List<Experience> experiences = dto.getExperiences()
                    .stream()
                    .map(experienceRequestDto -> {
                        Experience experience = ExperienceMapper.toEntity(experienceRequestDto);
                        experience.setCv(cv); // lidhim për bidirectional mapping
                        return experience;
                    })
                    .collect(Collectors.toList());

            cv.setExperiences(experiences);
        }

        return cv;
    }

    // Entity -> CVDto
    public static CVDto toDto(CV cv) {
        CVDto dto = new CVDto();
        dto.setId(cv.getId());
        dto.setUserId(cv.getUserId());
        dto.setFullname(cv.getFullname());
        dto.setEmail(cv.getEmail());
        dto.setPhoneNumber(cv.getPhoneNumber());
        dto.setJobTitle(cv.getJobTitle());
        dto.setSummary(cv.getSummary());

        if (cv.getExperiences() != null) {
            List<ExperienceDto> experiences = cv.getExperiences()
                    .stream()
                    .map(ExperienceMapper::toDto)
                    .collect(Collectors.toList());

            dto.setExperiences(experiences);
        }

        return dto;
    }
}
