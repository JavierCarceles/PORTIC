package net.portic.gestorComprobacionNotificacionMMPP.domain.mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.UserConfigDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.UserConfig;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface UserConfigMapper {

    UserConfigMapper INSTANCE = Mappers.getMapper(UserConfigMapper.class);

    UserConfig fromDtoToEntity(UserConfigDto userConfig);

    UserConfigDto fromEntityToDto(UserConfig userConfig);

}
