package net.portic.gestorComprobacionNotificacionMMPP.domain.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "BLOCI_IFTDGN_CABECERA")
@IdClass(ProgAndRequestIds.class)
public class Request implements Serializable {

    @Id
    @Column(name="DOC_INTERNO")
    private long internalDoc;

    @Id
    @Column(name="VS_INTERNA")
    private int internalVs;

    @Column(name="ID_EXP_ENTRADA", length = 15)
    private Long idExpEntry;

    @Column(name="ID_EXP_SALIDA", length = 15)
    private Long idExpDeparture;

    @Column(name="ID_EXP_TERRESTRE", length = 15)
    private Long idExpTerrestrial;

    @Column(name="ESTADO")
    private String status;

    @Column(name="FECHA")
    private Date date;

    @Column(name="NUM_DOCUMENT")
    private String documentNumber;

    @Column(name="NUM_REF_TRK")
    private long numRefTrk;

    @Column(name="NUM_MSG_TRK")
    private long numMsgTrk;

}
