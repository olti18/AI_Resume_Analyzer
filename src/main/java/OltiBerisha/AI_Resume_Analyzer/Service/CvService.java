package OltiBerisha.AI_Resume_Analyzer.Service;


import OltiBerisha.AI_Resume_Analyzer.Dto.CvDto;

import java.util.List;

public interface CvService {
    CvDto save(CvDto dto);
    List<CvDto> getAllByCurrentUser();
    CvDto getById(Long id);
}
