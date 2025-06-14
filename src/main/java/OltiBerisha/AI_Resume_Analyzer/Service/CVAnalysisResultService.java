package OltiBerisha.AI_Resume_Analyzer.Service;

import OltiBerisha.AI_Resume_Analyzer.Dto.CvAnalysisResultDto;
import OltiBerisha.AI_Resume_Analyzer.Model.CVAnalysisResult;

import java.util.List;

public interface CVAnalysisResultService {
    CvAnalysisResultDto save (Long cvId, CvAnalysisResultDto dto);
    List<CvAnalysisResultDto> getByCvId(Long cvId);
}
