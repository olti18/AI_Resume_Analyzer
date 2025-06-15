package OltiBerisha.AI_Resume_Analyzer.Mapper;

import OltiBerisha.AI_Resume_Analyzer.Dto.FavoriteJobDto;
import OltiBerisha.AI_Resume_Analyzer.Model.FavoriteJob;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FavoriteJobMapper {
    FavoriteJobDto toDto(FavoriteJob entity);
    FavoriteJob toEntity(FavoriteJobDto dto);
    List<FavoriteJobDto> toDtoList(List<FavoriteJob> list);
}

