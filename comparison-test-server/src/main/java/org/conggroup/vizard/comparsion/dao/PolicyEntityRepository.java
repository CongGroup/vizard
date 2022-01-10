package org.conggroup.vizard.comparsion.dao;

import org.conggroup.vizard.comparsion.entity.PolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PolicyEntityRepository extends JpaRepository<PolicyEntity, Long> {

    PolicyEntity findOneByOwnerId(Integer ownerId);

    @Query(value = "select ownerId from PolicyEntity")
    List<Integer> findAllOwnerId();
}
