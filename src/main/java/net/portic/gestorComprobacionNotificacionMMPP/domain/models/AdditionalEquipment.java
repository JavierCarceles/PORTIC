package net.portic.gestorComprobacionNotificacionMMPP.domain.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "BLOCI_IFTDGN_PROGRAMACIONES_EQUIPOS")
@IdClass(AdditionalEquipmentIds.class)
public class AdditionalEquipment implements Serializable {

    @Id
    @Column(name="DOC_INTERNO", length = 15, nullable = false)
    private long internalDoc;

    @Id
    @Column(name="VS_INTERNA", length = 6, nullable = false)
    private int internalVs;

    @Id
    @Column(name="NUM_EQUIPO", nullable = false)
    private int equipmentNumber;

    @Column(name="MATRICULA", length = 17)
    private String plate;

    @Column(name="REF_CONTENEDOR", length = 35)
    private String refContainer;

    @Column(name="EMISOR", length = 35)
    private String issuing;

    @Column(name="BOOKING", length = 35)
    private String booking;

    @Column(name="NUM_ENTREGUESE", length = 35)
    private String numEntreguese;

    @Column(name="DEPOSITO", length = 35)
    private String deposit;

    @Column(name="FECHA_CADUCIDAD")
    private LocalDateTime expirationDate;

    @Column(name="FECHA_PREV_RECOGIDA_ENT")
    private LocalDateTime datePrevEntr;

    @Column(name="FECHA_PREV_RECOGIDA_PR")
    private LocalDateTime datePrevPr;

    @Column(name="ESTADO_CONTENEDOR", length = 4)
    private String state;

    @Column(name="NUM_REF_FICHERO", length = 35)
    private String numRefFile;

    @Column(name="NUM_MENSAJE", length = 35)
    private String numMsg;

    @Column(name="FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;

    @Column(name="PROCESO", length = 10)
    private String proceso;

    @Column(name="NUM_CODECO", length = 10)
    private String numCodeco;

    @Column(name = "ESMT_ID_MSG")
    private Long idEsmtMsg;

    @Transient
    private AppTrkMsg appTrkMsg;

}
