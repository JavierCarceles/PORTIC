package net.portic.gestorComprobacionNotificacionMMPP.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto implements Serializable {

    private long internalDoc;
    private int internalVs;
    private Long idExpEntry;
    private Long idExpDeparture;
    private Long idExpTerrestrial;
    private String status;
    private Date date;
    private String documentNumber;
    private long numRefTrk;
    private long numMsgTrk;

}
