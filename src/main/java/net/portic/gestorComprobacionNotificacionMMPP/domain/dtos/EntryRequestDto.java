package net.portic.gestorComprobacionNotificacionMMPP.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

//Contiene los parámetros de entrada para los dos servicios prinicpales
@Getter
@Setter
public class EntryRequestDto {

    @JsonProperty("dateFilter")
    private String dateFilter;

    @JsonProperty("hasAudit")
    private boolean hasAudit;
}
