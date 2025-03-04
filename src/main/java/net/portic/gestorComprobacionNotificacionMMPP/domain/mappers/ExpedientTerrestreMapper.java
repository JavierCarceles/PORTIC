package net.portic.gestorComprobacionNotificacionMMPP.domain.mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.ExpedientTerrestreDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.ExpedientTerrestre;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface ExpedientTerrestreMapper {

    ExpedientTerrestreMapper INSTANCE = Mappers.getMapper(ExpedientTerrestreMapper.class);

    ExpedientTerrestreDto fromEntityToDto(ExpedientTerrestre expedientTerrestre);

    ExpedientTerrestre fromDtoToEntity(ExpedientTerrestreDto expedientTerrestreDto);

}
