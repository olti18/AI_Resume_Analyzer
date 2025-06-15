package OltiBerisha.AI_Resume_Analyzer.Service;

import OltiBerisha.AI_Resume_Analyzer.Dto.AnalysisLogDto;

import java.util.List;

public interface AnalysisLogService {
    List<AnalysisLogDto> getByCvId(Long cvId);
    AnalysisLogDto save(AnalysisLogDto dto);

    //List<AnalysisLogDto> getAllForCurrentUser();

    //List<AnalysisLogDto> getByCvIdForCurrentUser(Long cvId);
}

