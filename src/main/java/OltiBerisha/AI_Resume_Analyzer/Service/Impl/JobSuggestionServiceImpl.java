package OltiBerisha.AI_Resume_Analyzer.Service.Impl;

import OltiBerisha.AI_Resume_Analyzer.Dto.JobSuggestionDto;
import OltiBerisha.AI_Resume_Analyzer.Mapper.JobSuggestionMapper;
import OltiBerisha.AI_Resume_Analyzer.Model.Cv;
import OltiBerisha.AI_Resume_Analyzer.Model.JobSuggestion;
import OltiBerisha.AI_Resume_Analyzer.Repository.CvRepository;
import OltiBerisha.AI_Resume_Analyzer.Repository.JobSuggestionRepository;
import OltiBerisha.AI_Resume_Analyzer.Service.JobSuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
@RequiredArgsConstructor
public class JobSuggestionServiceImpl implements JobSuggestionService {

    private final JobSuggestionRepository repository;
    private final JobSuggestionMapper mapper;
    private final CvRepository cvRepository;

    private String getCurrentUserId() {
        return getContext().getAuthentication().getName();
    }

    @Override
    public List<JobSuggestionDto> getSuggestionsForCv(Long cvId) {
        Cv cv = cvRepository.findById(cvId)
                .filter(c -> c.getUserId().equals(getCurrentUserId()))
                .orElseThrow(() -> new RuntimeException("Unauthorized access to CV"));
        List<JobSuggestion> suggestions = repository.findByCvId(cvId);
        return mapper.toDtoList(suggestions);
    }

    @Override
    public JobSuggestionDto getSuggestion(Long id) {
        JobSuggestion suggestion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Suggestion not found"));

        Cv cv = cvRepository.findById(suggestion.getCvId())
                .filter(c -> c.getUserId().equals(getCurrentUserId()))
                .orElseThrow(() -> new RuntimeException("Unauthorized"));

        return mapper.toDto(suggestion);
    }
}

