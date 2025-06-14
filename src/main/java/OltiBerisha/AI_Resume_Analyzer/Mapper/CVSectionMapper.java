package OltiBerisha.AI_Resume_Analyzer.Mapper;

import OltiBerisha.AI_Resume_Analyzer.Dto.CVSectionDto;
import OltiBerisha.AI_Resume_Analyzer.Model.CVSection;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CVSectionMapper {
    CVSectionDto toDto(CVSection entity);
    CVSection toEntity(CVSectionDto dto);
    List<CVSectionDto> toDtoList(List<CVSection> entities);
}
