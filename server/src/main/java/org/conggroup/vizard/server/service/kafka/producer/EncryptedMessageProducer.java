package org.conggroup.vizard.server.service.kafka.producer;

import org.conggroup.vizard.crypto.components.kafka.KafkaConstants;
import org.conggroup.vizard.crypto.components.VizardMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EncryptedMessageProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaProducer;

    public void produce(VizardMessage message) {
        String topicName = KafkaConstants.TOPIC_MESSAGE_ENCRYPTED;
        kafkaProducer.send(topicName, message.ownerId.toString(), message);
    }
}
