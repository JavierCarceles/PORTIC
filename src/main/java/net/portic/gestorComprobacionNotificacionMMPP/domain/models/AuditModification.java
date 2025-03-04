package net.portic.gestorComprobacionNotificacionMMPP.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(AuditModificationIds.class)
@Table(name = "BLOCI_IFTDGN_COMPROBACIONES_MMPP_MODIFICACIONES")
public class AuditModification implements Serializable {

    @Id
    @Column(name="ID_AUDIT", length=14, nullable = false)
    private long idAudit;

    @Id
    @Column(name="ID_MODIFICACION", nullable = false)
    private int idModification;

    @Column(name="ID_PROGRAMACION", nullable = false)
    private int idProgramming;

    @Column(name="ESTADO_PROG_ANTERIOR")
    private String oldProgStatus;

    @Column(name="ESTADO_PROG_ACTUAL")
    private String presentProgStatus;

    @Column(name="ESTADO_SOLI_ANTERIOR")
    private String oldRequestStatus;

    @Column(name="ESTADO_SOLI_ACTUAL")
    private String presentRequestStatus;

    @Column(name="EMAILS_RECEPTORES")
    private String receptorEmails;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_AUDIT", insertable = false, updatable = false)
    private Audit audit;

}
