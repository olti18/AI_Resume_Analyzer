package OltiBerisha.AI_Resume_Analyzer.Mapper;

import OltiBerisha.AI_Resume_Analyzer.Dto.AnalysisLogDto;
import OltiBerisha.AI_Resume_Analyzer.Model.AnalysisLog;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnalysisLogMapper {
    AnalysisLogDto toDto(AnalysisLog entity);
    AnalysisLog toEntity(AnalysisLogDto dto);
    List<AnalysisLogDto> toDtoList(List<AnalysisLog> list);
}

