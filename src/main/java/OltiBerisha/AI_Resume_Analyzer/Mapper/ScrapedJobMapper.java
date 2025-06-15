package OltiBerisha.AI_Resume_Analyzer.Mapper;

import OltiBerisha.AI_Resume_Analyzer.Dto.ScrapedJobDto;
import OltiBerisha.AI_Resume_Analyzer.Model.ScrapedJob;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScrapedJobMapper {
    ScrapedJobDto toDto(ScrapedJob entity);
    ScrapedJob toEntity(ScrapedJobDto dto);
    List<ScrapedJobDto> toDtoList(List<ScrapedJob> list);
}

