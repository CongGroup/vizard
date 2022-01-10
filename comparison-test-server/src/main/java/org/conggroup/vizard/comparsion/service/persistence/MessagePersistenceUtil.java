package org.conggroup.vizard.comparsion.service.persistence;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.VizardMessage;
import org.conggroup.vizard.comparsion.dao.MessageEntityRepository;
import org.conggroup.vizard.comparsion.entity.MessageEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessagePersistenceUtil {

    private final MessageEntityRepository messageEntityRepository;

    public void saveEntity(MessageEntity entity) {
        messageEntityRepository.save(entity);
    }

    public void saveEntities(ArrayList<MessageEntity> entities) {
        messageEntityRepository.saveAll(entities);
    }

    public void saveAll(ArrayList<VizardMessage> messages) {
        List<MessageEntity> entities = messages.stream()
                .map(MessageEntity::new)
                .collect(Collectors.toList());
        messageEntityRepository.saveAll(entities);
    }

    public void save(VizardMessage message) {
        MessageEntity tmp = new MessageEntity(message);
        messageEntityRepository.save(tmp);
    }

    public HashMap<Integer, ArrayList<VizardMessage>> searchRecords(Long beginSeqIndex, Long endSeqIndex) {
        HashMap<Integer, ArrayList<VizardMessage>> res = new HashMap<Integer, ArrayList<VizardMessage>>();

        ArrayList<MessageEntity> tmpList = messageEntityRepository.findAllBySeqIndexBetween(beginSeqIndex, endSeqIndex);
        for(MessageEntity me: tmpList) {
            VizardMessage m = me.vizardMessage();
            if(!res.containsKey(m.ownerId)) {
                res.put(m.ownerId, new ArrayList<VizardMessage>());
            }
            res.get(m.ownerId).add(m);
        }

        return res;
    }
}
