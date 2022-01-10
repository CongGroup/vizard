package org.conggroup.vizard.server.service.kafka.comsumer;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.kafka.KafkaConstants;
import org.conggroup.vizard.crypto.components.dpf.DPFKey;
import org.conggroup.vizard.server.service.persistence.DPFKeyPersistenceUtil;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DPFKeyConsumer {

    private final DPFKeyPersistenceUtil dpfKeyPersistenceUtil;

    @KafkaListener(topics = KafkaConstants.TOPIC_POLICY_DPFKEY, containerFactory = "localKafkaListenerContainerFactory")
    public void consume(DPFKey dpfKey) {
        dpfKeyPersistenceUtil.save(dpfKey);
    }

    @KafkaListener(topics = KafkaConstants.TOPIC_POLICY_DPFKEY_REVOKE, containerFactory = "localKafkaListenerContainerFactory")
    public void consume(Integer ownerId) { dpfKeyPersistenceUtil.delete(ownerId); }
}
