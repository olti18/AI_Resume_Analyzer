package OltiBerisha.AI_Resume_Analyzer.Service;

import OltiBerisha.AI_Resume_Analyzer.Dto.ScrapedJobDto;

import java.util.List;

public interface ScrapedJobService {
    List<ScrapedJobDto> getAll();
    ScrapedJobDto getById(Long id);
    ScrapedJobDto save(ScrapedJobDto dto);
    void delete(Long id);
}

