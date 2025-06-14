package OltiBerisha.AI_Resume_Analyzer.Service.Impl;


import OltiBerisha.AI_Resume_Analyzer.Config.KeycloakUtils;
import OltiBerisha.AI_Resume_Analyzer.Dto.CVSectionDto;
import OltiBerisha.AI_Resume_Analyzer.Mapper.CVSectionMapper;
import OltiBerisha.AI_Resume_Analyzer.Model.CVSection;
import OltiBerisha.AI_Resume_Analyzer.Model.Cv;
import OltiBerisha.AI_Resume_Analyzer.Repository.CvRepository;
import OltiBerisha.AI_Resume_Analyzer.Repository.CvSectionRepository;
import OltiBerisha.AI_Resume_Analyzer.Service.CVSectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
@RequiredArgsConstructor
public class CVSectionServiceImpl implements CVSectionService {

    private final CvSectionRepository repository;
    private final CvRepository cvRepository;
    private final CVSectionMapper mapper;


    @Override
    public CVSectionDto save(Long cvId, CVSectionDto dto) {
        Cv cv = cvRepository.findById(cvId)
                .filter(c -> c.getUserId().equals(KeycloakUtils.getCurrentUserId()))
                .orElseThrow(() -> new RuntimeException("CV not found or unauthorized"));

        CVSection section = mapper.toEntity(dto);
        section.setCvId(cvId);
        return mapper.toDto(repository.save(section));
    }

    @Override
    public List<CVSectionDto> getSectionsByCvId(Long cvId) {
        Cv cv = cvRepository.findById(cvId)
                .filter(c -> c.getUserId().equals(KeycloakUtils.getCurrentUserId()))
                .orElseThrow(() -> new RuntimeException("Unauthorized access to CV"));

        return mapper.toDtoList(repository.findByCvId(cvId));
    }
}

