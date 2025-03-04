package net.portic.gestorComprobacionNotificacionMMPP.domain.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MessageAttributesIds implements Serializable {
    private long messageId;
    private int formId;
    private int type;
}
