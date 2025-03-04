package net.portic.gestorComprobacionNotificacionMMPP.domain.mappers;


import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.CompanyDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.Company;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    Company fromDtoToEntity(CompanyDto company);

    CompanyDto fromEntityToDto(Company company);

}
