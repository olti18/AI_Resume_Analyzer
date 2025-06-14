package OltiBerisha.AI_Resume_Analyzer.Service.Impl;

import OltiBerisha.AI_Resume_Analyzer.Config.KeycloakUtils;
import OltiBerisha.AI_Resume_Analyzer.Dto.CvDto;
import OltiBerisha.AI_Resume_Analyzer.Mapper.CvMapper;
import OltiBerisha.AI_Resume_Analyzer.Model.Cv;
import OltiBerisha.AI_Resume_Analyzer.Repository.CvRepository;
import OltiBerisha.AI_Resume_Analyzer.Service.CvService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CvServiceImpl implements CvService {

    private final CvRepository repository;
    private final CvMapper mapper;

    @Override
    public CvDto save(CvDto dto) {
        Cv entity = mapper.toEntity(dto);
        entity.setUserId(KeycloakUtils.getCurrentUserId());
        entity.setUploadDate(LocalDateTime.now());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public List<CvDto> getAllByCurrentUser() {
        return mapper.toDtoList(repository.findByUserId(KeycloakUtils.getCurrentUserId()));
    }

    @Override
    public CvDto getById(Long id) {
        Cv cv = repository.findById(id)
                .filter(c -> c.getUserId().equals(KeycloakUtils.getCurrentUserId()))
                .orElseThrow(() -> new RuntimeException("Cv not found or not authorized"));
        return mapper.toDto(cv);
    }
}
