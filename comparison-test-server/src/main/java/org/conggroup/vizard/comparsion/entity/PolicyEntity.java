package org.conggroup.vizard.comparsion.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.conggroup.vizard.crypto.components.policy.Policy;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "test_vizard_policy")
public class PolicyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="owner_id")
    private Integer ownerId;

    @Column(name="conditions")
    private String conditions;

    @Column(name="condition_type")
    private String conditionType;

    @Column(name="not_flag", columnDefinition = "BOOLEAN")
    private Boolean not_flag;

    public PolicyEntity() {}

    public PolicyEntity(Policy policy) {
        this.ownerId = policy.ownerId;
        this.conditions = String.join(",",policy.conditions);
        this.conditionType = policy.conditionType;
        this.not_flag = policy.not;
    }

    public Policy policy() {
        Policy tmp = new Policy();
        tmp.ownerId = ownerId;
        tmp.conditions = conditions.split(",");
        tmp.conditionType = conditionType;
        tmp.not = not_flag;
        return tmp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PolicyEntity that = (PolicyEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
