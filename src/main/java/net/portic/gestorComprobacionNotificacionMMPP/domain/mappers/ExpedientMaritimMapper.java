package net.portic.gestorComprobacionNotificacionMMPP.domain.mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.ExpedientMaritimDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.ExpedientMaritim;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface ExpedientMaritimMapper {

    ExpedientMaritimMapper INSTANCE = Mappers.getMapper(ExpedientMaritimMapper.class);

    ExpedientMaritimDto fromEntityToDto(ExpedientMaritim expedientMaritim);

    ExpedientMaritim fromDtoToEntity(ExpedientMaritimDto expedientMaritimDto);

}
