package OltiBerisha.AI_Resume_Analyzer.Service;

import OltiBerisha.AI_Resume_Analyzer.Dto.JobSuggestionDto;

import java.util.List;

public interface JobSuggestionService {
    List<JobSuggestionDto> getSuggestionsForCv(Long cvId);
    JobSuggestionDto getSuggestion(Long id);
}
