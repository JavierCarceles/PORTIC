package net.portic.gestorComprobacionNotificacionMMPP.domain.mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AdditionalEquipmentDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.AdditionalEquipment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface AdditionalEquipmentMapper {

    AdditionalEquipmentMapper INSTANCE = Mappers.getMapper(AdditionalEquipmentMapper.class);

    AdditionalEquipmentDto fromEntityToDto(AdditionalEquipment additionalEquipment);

    AdditionalEquipment fromDtoToEntity(AdditionalEquipmentDto additionalEquipmentDto);

}
