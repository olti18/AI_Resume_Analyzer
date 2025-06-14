package OltiBerisha.AI_Resume_Analyzer.Mapper;

import OltiBerisha.AI_Resume_Analyzer.Dto.CvAnalysisResultDto;
import OltiBerisha.AI_Resume_Analyzer.Model.CVAnalysisResult;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CvAnalysisResultMapper {
    CvAnalysisResultDto toDto(CVAnalysisResult entity);
    CVAnalysisResult toEntity(CvAnalysisResultDto dto);
    List<CvAnalysisResultDto> toDtoList(List<CVAnalysisResult> entities);
}
