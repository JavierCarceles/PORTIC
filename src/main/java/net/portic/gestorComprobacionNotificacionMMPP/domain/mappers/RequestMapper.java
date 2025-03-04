package net.portic.gestorComprobacionNotificacionMMPP.domain.mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.RequestDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.Request;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface RequestMapper {

    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);

    RequestDto fromEntityToDto(Request request);

    Request fromDtoToEntity(RequestDto requestDto);

}
