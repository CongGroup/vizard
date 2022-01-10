package org.conggroup.vizard.server.entity;

import lombok.*;
import org.conggroup.vizard.crypto.utils.Converter;
import org.conggroup.vizard.crypto.components.dpf.DPFKey;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dpf_key")
@Getter
@Setter
@ToString
public class DPFKeyEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="owner_id")
    private Integer ownerId;

    @Column(name="key_index")
    private Integer keyIndex;

    @Column(name="k")
    private String k;

    @Column(name="condition_type")
    private String conditionType;

    @Column(name="not_flag", columnDefinition = "BOOLEAN")
    private Boolean not_flag;

    public DPFKeyEntity() {}

    public DPFKeyEntity(DPFKey dpfKey) {
        this.ownerId = dpfKey.ownerId;
        this.keyIndex = dpfKey.keyIndex;
        this.k = Converter.bytesToString(dpfKey.k);
        this.conditionType = dpfKey.conditionType;
        this.not_flag = dpfKey.not_flag;
    }

    public DPFKey dpfKey() {
        DPFKey tmp = new DPFKey();
        tmp.ownerId = ownerId;
        tmp.keyIndex = keyIndex;
        tmp.k = Converter.stringToBytes(k);
        tmp.conditionType = conditionType;
        tmp.not_flag = not_flag;
        return tmp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DPFKeyEntity that = (DPFKeyEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
