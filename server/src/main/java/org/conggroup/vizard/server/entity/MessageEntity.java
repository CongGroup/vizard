package org.conggroup.vizard.server.entity;

import lombok.*;
import org.conggroup.vizard.crypto.components.VizardMessage;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "vizard_message")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="owner_id")
    private Integer ownerId;

    @Column(name="seq_index")
    private Long seqIndex;

    @Column(name="c")
    private String c;

    public MessageEntity() {}

    public MessageEntity(VizardMessage vizardMessage) {
        this.ownerId = vizardMessage.ownerId;
        this.seqIndex = vizardMessage.seqIndex;
        this.c = vizardMessage.c.toString();
    }

    public VizardMessage vizardMessage() {
        VizardMessage tmp = new VizardMessage();
        tmp.ownerId = ownerId;
        tmp.seqIndex = seqIndex;
        tmp.c = new BigInteger(c);
        return tmp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MessageEntity that = (MessageEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
