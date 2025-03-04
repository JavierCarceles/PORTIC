package net.portic.gestorComprobacionNotificacionMMPP.domain.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NamedNativeQuery(name = "GlobalEquipment.getLastDummy",
        query = "SELECT MAX(TO_NUMBER(SUBSTR(MATRICULA, 9, 3))) " +
                "FROM BLOCI_IFTDGN_EQUIPAMIENTOS " +
                "WHERE MATRICULA LIKE 'XXXXX111%' " +
                "AND DOC_INTERNO = ?1 " +
                "AND VS_INTERNA = ?2 ")
@Table(name = "BLOCI_IFTDGN_EQUIPAMIENTOS")
@IdClass(GlobalEquipmentIds.class)
public class GlobalEquipment implements Serializable {

    @Id
    @Column(name="DOC_INTERNO", length = 15, nullable = false)
    private long internalDoc;

    @Id
    @Column(name="VS_INTERNA", length = 6, nullable = false)
    private int internalVs;

    @Id
    @Column(name="MATRICULA", length = 17, nullable = false)
    private String plate;

    @Column(name="CODIGO", length = 3)
    private String code;

    @Column(name="TIPO_CONTENEDOR", length = 4)
    private String containerType;

    @Column(name="GRUPAJE", length = 3)
    private int groupage;

    @Column(name="BULTOS", length = 8)
    private int packages;

    @Column(name="PESO_BRUTO", length = 18)
    private Integer grossWeight;

    @Column(name="PESO_NETO", length = 18)
    private Integer netWeight;

    @Column(name="LUGAR_ESTIBA", length = 9)
    private String stowagePlace;

    @Transient
    private AdditionalEquipment additionalEquipment;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "DOC_INTERNO", referencedColumnName = "DOC_INTERNO", insertable = false, updatable = false),
            @JoinColumn(name = "VS_INTERNA", referencedColumnName = "VS_INTERNA", insertable = false, updatable = false)
    })
    private Programming programming;

}
