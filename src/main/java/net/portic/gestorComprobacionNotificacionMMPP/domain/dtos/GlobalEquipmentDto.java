package net.portic.gestorComprobacionNotificacionMMPP.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalEquipmentDto implements Serializable {

    private long internalDoc;
    private int internalVs;
    private String plate;
    private String code;
    private String containerType;
    private int groupage;
    private int packages;
    private int grossWeight;
    private int netWeight;
    private String stowagePlace;
    private AdditionalEquipmentDto additionalEquipmentDto;

}
