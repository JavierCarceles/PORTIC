package net.portic.gestorComprobacionNotificacionMMPP.domain.mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.AuditModificationDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.AuditModification;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface AuditModificationMapper {

    AuditModificationMapper INSTANCE = Mappers.getMapper(AuditModificationMapper.class);

    AuditModificationDto fromEntityToDto(AuditModification auditModification);

    AuditModification fromDtoToEntity(AuditModificationDto auditModification);

    List<AuditModification> fromDtosToEntities(List<AuditModificationDto> list);

}
