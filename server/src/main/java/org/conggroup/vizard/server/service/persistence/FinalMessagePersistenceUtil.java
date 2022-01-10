package org.conggroup.vizard.server.service.persistence;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.VizardMessage;
import org.conggroup.vizard.server.dao.MessageEntityRepository;
import org.conggroup.vizard.server.entity.MessageEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class FinalMessagePersistenceUtil {

    private final MessageEntityRepository messageEntityRepository;

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
