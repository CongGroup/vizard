package org.conggroup.vizard.server.dao;

import org.conggroup.vizard.server.entity.DPFKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public interface DPFKeyEntityRepository extends JpaRepository<DPFKeyEntity, Integer> {
    ArrayList<DPFKeyEntity> findAllByOwnerId(Integer ownerId);
    ArrayList<DPFKeyEntity> findAllByOwnerIdAndKeyIndexIn(Integer ownerId, List<Integer> keyIndexes);

    @Modifying
    @Transactional
    void deleteAllByOwnerId(Integer ownerId);
}
