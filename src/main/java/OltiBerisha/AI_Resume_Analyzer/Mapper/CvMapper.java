package OltiBerisha.AI_Resume_Analyzer.Mapper;

import OltiBerisha.AI_Resume_Analyzer.Dto.CvDto;
import OltiBerisha.AI_Resume_Analyzer.Model.Cv;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CvMapper {
    CvDto toDto(Cv entity);
    Cv toEntity(CvDto dto);
    List<CvDto> toDtoList(List<Cv> entities);
}
