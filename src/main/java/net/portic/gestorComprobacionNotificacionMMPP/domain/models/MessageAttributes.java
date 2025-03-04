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
@NamedQuery(name="MessageAttributes.getMaxMsgIdByNumEntreguese",
    query = " SELECT MAX(msgAttr.messageId) " +
            " FROM MessageAttributes msgAttr " +
            " WHERE msgAttr.messageTypeId = 136" +
            " AND msgAttr.content = ?1 ")
@Table(name = "MESSAGE_ATTRIBUTES") //Esquema REINGENIERIA
@IdClass(MessageAttributesIds.class)
public class MessageAttributes implements Serializable {

    @Id
    @Column(name="MA_MESSAGE_ID", length=14, nullable = false)
    private long messageId;

    @Id
    @Column(name="MA_FORM_ID", length=14, nullable = false)
    private int formId;

    @Id
    @Column(name="MA_TYPE", length=14, nullable = false)
    private int type;

    @Column(name="MA_MESSAGE_TYPE_ID", length=14, nullable = false)
    private int messageTypeId;

    @Column(name="MA_CONTENT", length=14, nullable = false)
    private String content;
}
