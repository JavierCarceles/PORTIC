package net.portic.gestorComprobacionNotificacionMMPP.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgrammingDto implements Serializable {

    private long idProgramming;
    private String status;
    private long internalDoc;
    private int internalVs;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;
    private String creatorUser;
    private String notifyIncidences;
    private String expType;
    private long idExp;
    private String nifCompanyUser;

    private RequestDto request;
    private ExpedientMaritimDto expedientMaritim;
    private ExpedientTerrestreDto expedientTerrestre;
    private UserConfigDto userConfig;

    private List<GlobalEquipmentDto> globalEquipmentDtosList;

}
