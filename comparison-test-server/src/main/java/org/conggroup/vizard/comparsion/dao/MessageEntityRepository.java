package org.conggroup.vizard.comparsion.dao;

import org.conggroup.vizard.comparsion.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface MessageEntityRepository extends JpaRepository<MessageEntity, Long> {

    ArrayList<MessageEntity> findAllBySeqIndexBetween(Long beginSeqIndex, Long endSeqIndex);

}
