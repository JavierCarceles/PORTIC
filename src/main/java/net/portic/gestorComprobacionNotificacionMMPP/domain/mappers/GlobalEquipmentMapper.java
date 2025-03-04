package net.portic.gestorComprobacionNotificacionMMPP.domain.mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.GlobalEquipmentDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.GlobalEquipment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface GlobalEquipmentMapper {

    GlobalEquipmentMapper INSTANCE = Mappers.getMapper(GlobalEquipmentMapper.class);

    GlobalEquipmentDto fromEntityToDto(GlobalEquipment globalEquipment);

    GlobalEquipment fromDtoToEntity(GlobalEquipmentDto globalEquipmentDto);

    List<GlobalEquipmentDto> fromEntitiesToDtos(List<GlobalEquipment> list);

}
