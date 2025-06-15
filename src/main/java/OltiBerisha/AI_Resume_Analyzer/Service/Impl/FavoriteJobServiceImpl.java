package OltiBerisha.AI_Resume_Analyzer.Service.Impl;

import OltiBerisha.AI_Resume_Analyzer.Config.KeycloakUtils;
import OltiBerisha.AI_Resume_Analyzer.Dto.FavoriteJobDto;
import OltiBerisha.AI_Resume_Analyzer.Mapper.FavoriteJobMapper;
import OltiBerisha.AI_Resume_Analyzer.Model.FavoriteJob;
import OltiBerisha.AI_Resume_Analyzer.Repository.FavoriteJobRepository;
import OltiBerisha.AI_Resume_Analyzer.Service.FavoriteJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteJobServiceImpl implements FavoriteJobService {
    private final FavoriteJobRepository repository;
    private final FavoriteJobMapper mapper;
    @Override
    public List<FavoriteJobDto> getAllForCurrentUser() {
        String userId = KeycloakUtils.getCurrentUserId();
        return mapper.toDtoList(repository.findByUserId(userId));
    }

    @Override
    public FavoriteJobDto save(Long jobSuggestionId) {
        FavoriteJob job = new FavoriteJob();
        job.setUserId(KeycloakUtils.getCurrentUserId());
        job.setJobSuggestionId(jobSuggestionId);
        job.setSavedAt(LocalDateTime.now());
        return mapper.toDto(repository.save(job));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}

