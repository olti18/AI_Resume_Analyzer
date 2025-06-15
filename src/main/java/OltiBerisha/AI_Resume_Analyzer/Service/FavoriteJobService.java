package OltiBerisha.AI_Resume_Analyzer.Service;

import OltiBerisha.AI_Resume_Analyzer.Dto.FavoriteJobDto;

import java.util.List;

public interface FavoriteJobService {
    List<FavoriteJobDto> getAllForCurrentUser();
    FavoriteJobDto save(Long jobSuggestionId);
    void delete(Long id);
}

