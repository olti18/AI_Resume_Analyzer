package OltiBerisha.AI_Resume_Analyzer.Mapper;

import OltiBerisha.AI_Resume_Analyzer.Dto.ExperienceDto;
import OltiBerisha.AI_Resume_Analyzer.Dto.ExperienceRequestDto;
import OltiBerisha.AI_Resume_Analyzer.Model.Experience;
import org.springframework.stereotype.Component;

@Component
public class ExperienceMapper {

    public static Experience toEntity(ExperienceDto dto) {
        Experience experience = new Experience();
        experience.setCompany(dto.getCompany());
        experience.setPosition(dto.getPosition());
        return experience;
    }


    // Mapper për ExperienceRequestDto -> Entity
    public static Experience toEntity(ExperienceRequestDto dto) {
        Experience experience = new Experience();
        experience.setPosition(dto.getPosition());
        experience.setCompany(dto.getCompany());
        experience.setYears(dto.getYears());
        return experience;
    }

    // Mapper për Entity -> ExperienceDto
    public static ExperienceDto toDto(Experience experience) {
        ExperienceDto dto = new ExperienceDto();
        dto.setId(experience.getId());
        dto.setPosition(experience.getPosition());
        dto.setCompany(experience.getCompany());
        dto.setYears(experience.getYears());
        return dto;
    }
}
