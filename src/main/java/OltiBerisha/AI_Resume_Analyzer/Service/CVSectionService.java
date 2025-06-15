package OltiBerisha.AI_Resume_Analyzer.Service;

import OltiBerisha.AI_Resume_Analyzer.Dto.CVSectionDto;
import OltiBerisha.AI_Resume_Analyzer.Model.CVSection;

import java.util.List;

public interface CVSectionService {

    CVSectionDto save(Long cvId, CVSectionDto dto);
    List<CVSectionDto> getSectionsByCvId(Long cvId);

    //List<CVSectionDto> getSectionsByCvIdForCurrentUser(Long cvId);
}
