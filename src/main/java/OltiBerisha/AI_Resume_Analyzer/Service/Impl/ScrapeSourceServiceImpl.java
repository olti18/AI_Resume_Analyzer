package OltiBerisha.AI_Resume_Analyzer.Service.Impl;

import OltiBerisha.AI_Resume_Analyzer.Dto.ScrapeSourceDto;
import OltiBerisha.AI_Resume_Analyzer.Mapper.ScrapeSourceMapper;
import OltiBerisha.AI_Resume_Analyzer.Model.ScrapeSource;
import OltiBerisha.AI_Resume_Analyzer.Repository.ScrapedSourceRepository;
import OltiBerisha.AI_Resume_Analyzer.Service.ScrapeSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapeSourceServiceImpl implements ScrapeSourceService {
    private final ScrapedSourceRepository repository;
    private final ScrapeSourceMapper mapper;

    @Override
    public List<ScrapeSourceDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public ScrapeSourceDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Override
    public ScrapeSourceDto save(ScrapeSourceDto dto) {
        ScrapeSource entity = mapper.toEntity(dto);
        entity.setLastScrapeTime(LocalDateTime.now());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}

