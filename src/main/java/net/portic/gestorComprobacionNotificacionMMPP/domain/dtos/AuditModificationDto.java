package net.portic.gestorComprobacionNotificacionMMPP.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditModificationDto {

    @JsonProperty("idAudit")
    private long idAudit;

    @JsonProperty("idModification")
    private int idModification;

    @JsonProperty("idProgramming")
    private int idProgramming;

    @JsonProperty("oldProgrammingStatus")
    private String oldProgStatus;

    @JsonProperty("presentProgrammingStatus")
    private String presentProgStatus;

    @JsonProperty("oldRequestStatus")
    private String oldRequestStatus;

    @JsonProperty("presentRequestStatus")
    private String presentRequestStatus;

    @JsonProperty("receptorEmails")
    private String receptorEmails;

}
