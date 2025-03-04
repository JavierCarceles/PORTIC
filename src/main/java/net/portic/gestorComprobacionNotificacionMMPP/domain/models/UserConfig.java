package net.portic.gestorComprobacionNotificacionMMPP.domain.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "BLOCI_CONFIGURACION")
public class UserConfig implements Serializable {

    @Id
    @Column(name="USUARIO", nullable = false)
    private String user;

    @Column(name="ID_EMPRESA", nullable = false)
    private long  idCompany;

    @Column(name="AVISO_MMPP_EMAIL")
    private String email;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EMPRESA", referencedColumnName = "EMPRESA_2", insertable = false, updatable = false)
    private Company company;

}
