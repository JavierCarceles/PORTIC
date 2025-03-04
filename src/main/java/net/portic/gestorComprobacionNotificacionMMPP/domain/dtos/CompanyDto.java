package net.portic.gestorComprobacionNotificacionMMPP.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    @JsonProperty("idCompany")
    private long idCompany;

    @JsonProperty("nifCompnay")
    private String nifCompany;

}
