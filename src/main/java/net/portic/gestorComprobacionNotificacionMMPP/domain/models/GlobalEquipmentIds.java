package net.portic.gestorComprobacionNotificacionMMPP.domain.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GlobalEquipmentIds implements Serializable {

    private long internalDoc;
    private int internalVs;
    private String plate;

}
