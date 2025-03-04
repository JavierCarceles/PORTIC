package net.portic.gestorComprobacionNotificacionMMPP.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BLOCI_IFTDGN_COMPROBACIONES_MMPP")
public class Audit implements Serializable {

    @Id
    @Column(name="ID", length=14, nullable = false)
    private long id;

    @Column(name="SERVICIO", nullable = false)
    private String service;

    @Column(name="FECHA", nullable = false)
    private LocalDateTime date;

    @Column(name="NUM_COMPROBADOS", nullable = false)
    private int numChecked;

    @Column(name="NUM_MODIFICADOS", nullable = false)
    private int numModified;

    @Column(name="NUM_INCIDENCIAS", nullable = false)
    private int numIncidences;

    @Column(name="NUM_CORREOS_ENVIADOS", nullable = false)
    private int numSentEmails;


    @OneToMany(mappedBy = "audit",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<AuditModification> auditModificationList;
}
