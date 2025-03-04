package net.portic.gestorComprobacionNotificacionMMPP.domain.mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AppTrkMsgDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.AppTrkMsg;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface AppTrkMsgMapper {

    AppTrkMsgMapper INSTANCE = Mappers.getMapper(AppTrkMsgMapper.class);

    AppTrkMsg fromDtoToEntity(AppTrkMsgDto appTrkMsg);

    AppTrkMsgDto fromEntityToDto(AppTrkMsg appTrkMsg);
}
