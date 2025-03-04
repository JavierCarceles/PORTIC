package net.portic.gestorComprobacionNotificacionMMPP.domain.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@IdClass(AppTrkMsgIds.class)
@Table(name = "APP_TRK_MESSAGES", schema = "ECX")
public class AppTrkMsg implements Serializable {

    @Id
    @Column(name="NUM_REF_FICHERO")
    private long numRefFile;

    @Id
    @Column(name = "NUM_MENSAJE")
    private long numMsg;

    @Column(name = "ESTADO_PROCESO")
    private String processStatus;

    @Column(name = "FECHA_INICIO_PROCESO")
    private LocalDateTime processStartDate;

    @Column(name = "ESMT_ID_MSG")
    private long idEsmtMsg;

}
