package net.portic.gestorComprobacionNotificacionMMPP.domain.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NamedQuery(name = "Programming.getListProgrammings",
    query = "SELECT prog FROM Programming prog WHERE " +
            " prog.creationDate >= ?1 " +
            " AND prog.status = 'PR' " +
            " AND prog.expType IN('M', 'T') " )
@NamedQuery(name = "Programming.getListExpiredProgrammings",
        query = "SELECT prog FROM Programming prog WHERE " +
                " prog.creationDate >= ?1 " +
                " AND prog.status = 'PR' " +
                " AND prog.expType IN('M', 'T') " +
                " AND prog.expirationDate < CURRENT_DATE ")
@Table(name = "BLOCI_IFTDGN_PROGRAMACIONES")
@IdClass(ProgAndRequestIds.class)
public class Programming implements Serializable {

    @Column(name="ID_PROGRAMACION", nullable = false)
    private long idProgramming;

    @Column(name="ESTADO", length = 3)
    private String status;

    @Id
    @Column(name="DOC_INTERNO", length = 15, nullable = false)
    private long internalDoc;

    @Id
    @Column(name="VS_INTERNA", length = 6, nullable = false)
    private int internalVs;

    @Column(name="FECHA_CREACION")
    private LocalDateTime creationDate;

    @Column(name="FECHA_EXPIRACION")
    private LocalDateTime expirationDate;

    @Column(name="CREATION_USER")
    private String creatorUser;

    @Column(name="NOTIFICAR_INCIDENCIAS", length = 1)
    private String notifyIncidences;

    @Column(name="TIPO_EXP", length = 1)
    private String expType;

    @Column(name="ID_EXP")
    private long idExp;

    @Column(name="NIF_EMPRESA")
    private String nifCompanyUser;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "DOC_INTERNO", referencedColumnName = "DOC_INTERNO", insertable = false, updatable = false),
            @JoinColumn(name = "VS_INTERNA", referencedColumnName = "VS_INTERNA", insertable = false, updatable = false)
    })
    private Request request;

    @Transient
    private ExpedientMaritim expedientMaritim;

    @Transient
    private ExpedientTerrestre expedientTerrestre;

    @Transient
    private UserConfig userConfig;

    @OneToMany(mappedBy = "programming", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GlobalEquipment> globalEquipmentList;

}
