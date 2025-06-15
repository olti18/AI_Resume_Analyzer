package OltiBerisha.AI_Resume_Analyzer.Service;

import OltiBerisha.AI_Resume_Analyzer.Dto.CvAnalysisResultDto;
import OltiBerisha.AI_Resume_Analyzer.Model.Cv;

import java.util.List;

public interface CVAnalysisResultService {
    CvAnalysisResultDto save (Cv cvId, CvAnalysisResultDto dto);
    List<CvAnalysisResultDto> getByCvId(Long cvId);

    //List<CvAnalysisResultDto> getByCvIdForCurrentUser(Long cvId);
}
