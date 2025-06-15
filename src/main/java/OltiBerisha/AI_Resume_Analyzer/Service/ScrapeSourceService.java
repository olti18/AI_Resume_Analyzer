package OltiBerisha.AI_Resume_Analyzer.Service;

import OltiBerisha.AI_Resume_Analyzer.Dto.ScrapeSourceDto;

import java.util.List;

public interface ScrapeSourceService {
    List<ScrapeSourceDto> getAll();
    ScrapeSourceDto getById(Long id);
    ScrapeSourceDto save(ScrapeSourceDto dto);
    void delete(Long id);
}

