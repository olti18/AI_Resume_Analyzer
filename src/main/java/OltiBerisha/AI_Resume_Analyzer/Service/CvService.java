package OltiBerisha.AI_Resume_Analyzer.Service;

import OltiBerisha.AI_Resume_Analyzer.Dto.CVDto;

public interface CvService {
    CVDto findCvById(Long id);
}
