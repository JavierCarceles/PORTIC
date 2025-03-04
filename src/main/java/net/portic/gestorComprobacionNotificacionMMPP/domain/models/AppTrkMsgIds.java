package net.portic.gestorComprobacionNotificacionMMPP.domain.models;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class AppTrkMsgIds implements Serializable {
    private long numRefFile;
    private long numMsg;
}
