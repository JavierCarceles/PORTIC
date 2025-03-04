package net.portic.gestorComprobacionNotificacionMMPP.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalEquipmentIds implements Serializable {

    private long internalDoc;
    private int internalVs;
    private int equipmentNumber;

}
