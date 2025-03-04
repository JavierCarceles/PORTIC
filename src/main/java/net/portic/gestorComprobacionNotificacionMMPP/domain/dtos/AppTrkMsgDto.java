package net.portic.gestorComprobacionNotificacionMMPP.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class AppTrkMsgDto implements Serializable {

    @JsonProperty("numRefFile")
    private long numRefFile;

    @JsonProperty("numMsg")
    private long numMsg;

    @JsonProperty("processStatus")
    private String processStatus;

    @JsonProperty("processStartDate")
    private LocalDateTime processStartDate;

    private long idEsmtMsg;

    private AdditionalEquipmentDto additionalEquipment;

}
