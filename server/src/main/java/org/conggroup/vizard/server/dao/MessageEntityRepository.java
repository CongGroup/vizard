package org.conggroup.vizard.server.dao;

import org.conggroup.vizard.server.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface MessageEntityRepository extends JpaRepository<MessageEntity, Long> {

    ArrayList<MessageEntity> findAllBySeqIndexBetween(Long beginSeqIndex, Long endSeqIndex);

}
