package net.portic.gestorComprobacionNotificacionMMPP.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalEquipmentDto implements Serializable{

    private long internalDoc;
    private int internalVs;
    private int equipmentNumber;
    private String plate;
    private String refContainer;
    private String issuing;
    private String booking;
    private String numEntreguese;
    private String deposit;
    private LocalDateTime expirationDate;
    private LocalDateTime datePrevEntr;
    private LocalDateTime datePrevPr;
    private String state;
    private String numRefFile;
    private String numMsg;
    private LocalDateTime fechaActualizacion;
    private String proceso;
    private String numCodeco;

    private AppTrkMsgDto appTrkMsg;
    private GlobalEquipmentDto globalEquipment;
    private long idEsmtMsg;

}
