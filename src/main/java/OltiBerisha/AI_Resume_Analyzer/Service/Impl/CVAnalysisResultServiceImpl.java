package OltiBerisha.AI_Resume_Analyzer.Service.Impl;

import OltiBerisha.AI_Resume_Analyzer.Config.KeycloakUtils;
import OltiBerisha.AI_Resume_Analyzer.Dto.CvAnalysisResultDto;
import OltiBerisha.AI_Resume_Analyzer.Mapper.CvAnalysisResultMapper;
import OltiBerisha.AI_Resume_Analyzer.Model.CVAnalysisResult;
import OltiBerisha.AI_Resume_Analyzer.Repository.CVAnalysisResultRepository;
import OltiBerisha.AI_Resume_Analyzer.Service.CVAnalysisResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CVAnalysisResultServiceImpl implements CVAnalysisResultService {

    private final CVAnalysisResultRepository repository;
    private final CvAnalysisResultMapper mapper;

    @Override
    public CvAnalysisResultDto save(Long cvId, CvAnalysisResultDto dto) {
        CVAnalysisResult entity = mapper.toEntity(dto);
        entity.setCvId(cvId);
        entity.setAnalysisDate(LocalDateTime.now());
        entity.setUserId(KeycloakUtils.getCurrentUserId());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public List<CvAnalysisResultDto> getByCvId(Long cvId) {
        String currentUserId = KeycloakUtils.getCurrentUserId();
        return mapper.toDtoList(repository.findByCvIdAndUserId(cvId, currentUserId));
    }
}
