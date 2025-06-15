package OltiBerisha.AI_Resume_Analyzer.Service.Impl;

import OltiBerisha.AI_Resume_Analyzer.Dto.ScrapedJobDto;
import OltiBerisha.AI_Resume_Analyzer.Mapper.ScrapedJobMapper;
import OltiBerisha.AI_Resume_Analyzer.Model.ScrapedJob;
import OltiBerisha.AI_Resume_Analyzer.Repository.ScrapedJobRepository;
import OltiBerisha.AI_Resume_Analyzer.Service.ScrapedJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapedJobServiceImpl implements ScrapedJobService {
    private final ScrapedJobRepository repository;
    private final ScrapedJobMapper mapper;

    @Override
    public List<ScrapedJobDto> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public ScrapedJobDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Override
    public ScrapedJobDto save(ScrapedJobDto dto) {
        ScrapedJob entity = mapper.toEntity(dto);
        entity.setScrapeDate(LocalDateTime.now());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}

