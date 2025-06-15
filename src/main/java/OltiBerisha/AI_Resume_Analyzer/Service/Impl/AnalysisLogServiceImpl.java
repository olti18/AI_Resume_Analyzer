package OltiBerisha.AI_Resume_Analyzer.Service.Impl;

import OltiBerisha.AI_Resume_Analyzer.Dto.AnalysisLogDto;
import OltiBerisha.AI_Resume_Analyzer.Mapper.AnalysisLogMapper;
import OltiBerisha.AI_Resume_Analyzer.Model.AnalysisLog;
import OltiBerisha.AI_Resume_Analyzer.Repository.AnalysisLogRepository;
import OltiBerisha.AI_Resume_Analyzer.Service.AnalysisLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalysisLogServiceImpl implements AnalysisLogService {
    private final AnalysisLogRepository repository;
    private final AnalysisLogMapper mapper;

    @Override
    public List<AnalysisLogDto> getByCvId(Long cvId) {
        return mapper.toDtoList(repository.findByCvId(cvId));
    }

    @Override
    public AnalysisLogDto save(AnalysisLogDto dto) {
        AnalysisLog entity = mapper.toEntity(dto);
        entity.setStartTime(LocalDateTime.now());
        return mapper.toDto(repository.save(entity));
    }


}

