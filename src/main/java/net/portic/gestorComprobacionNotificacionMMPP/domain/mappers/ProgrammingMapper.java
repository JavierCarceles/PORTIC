package net.portic.gestorComprobacionNotificacionMMPP.domain.mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.ProgrammingDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.Programming;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface ProgrammingMapper {

    ProgrammingMapper INSTANCE = Mappers.getMapper(ProgrammingMapper.class);

    ProgrammingDto fromEntityToDto(Programming programming);

    Programming fromDtoToEntity(ProgrammingDto programmingDto);

    List<ProgrammingDto> fromEntitiesToDtos(List<Programming> programmingList);

}
