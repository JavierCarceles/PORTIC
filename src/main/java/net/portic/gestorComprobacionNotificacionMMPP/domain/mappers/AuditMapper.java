package net.portic.gestorComprobacionNotificacionMMPP.domain.mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AuditDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.Audit;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface AuditMapper {

    AuditMapper INSTANCE = Mappers.getMapper(AuditMapper.class);

    AuditDto fromEntityToDto(Audit audit);

    Audit fromDtoToEntity(AuditDto audit);
}
