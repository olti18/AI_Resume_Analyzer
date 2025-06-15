package OltiBerisha.AI_Resume_Analyzer.Mapper;

import OltiBerisha.AI_Resume_Analyzer.Dto.ScrapeSourceDto;
import OltiBerisha.AI_Resume_Analyzer.Model.ScrapeSource;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScrapeSourceMapper {
    ScrapeSourceDto toDto(ScrapeSource entity);
    ScrapeSource toEntity(ScrapeSourceDto dto);
    List<ScrapeSourceDto> toDtoList(List<ScrapeSource> list);
}
