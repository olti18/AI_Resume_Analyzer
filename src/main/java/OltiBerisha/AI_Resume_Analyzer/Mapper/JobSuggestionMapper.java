package OltiBerisha.AI_Resume_Analyzer.Mapper;

import OltiBerisha.AI_Resume_Analyzer.Dto.JobSuggestionDto;
import OltiBerisha.AI_Resume_Analyzer.Model.JobSuggestion;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobSuggestionMapper {
    JobSuggestionDto toDto(JobSuggestion entity);
    JobSuggestion toEntity(JobSuggestionDto dto);
    List<JobSuggestionDto> toDtoList(List<JobSuggestion> entities);
}

