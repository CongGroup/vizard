package org.conggroup.vizard.comparsion.service.message;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.comparsion.entity.MessageEntity;
import org.conggroup.vizard.comparsion.service.persistence.MessagePersistenceUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MessageProcessor {

    private final MessagePersistenceUtil messagePersistenceUtil;

    public void genMessage(Long beginSeq, Long numOfMessages, String c, Integer beginOwnerId, Integer endOwnerId) {
        ArrayList<MessageEntity> entities = new ArrayList<MessageEntity>();
        for(int ownerId = beginOwnerId; ownerId <= endOwnerId; ownerId++) {
            for(long i=beginSeq; i<beginSeq+numOfMessages; i++) {
                MessageEntity entity = new MessageEntity();
                entity.setOwnerId(ownerId);
                entity.setSeqIndex(i);
                entity.setC(c);
                entities.add(entity);
            }
        }
        messagePersistenceUtil.saveEntities(entities);
    }

}
