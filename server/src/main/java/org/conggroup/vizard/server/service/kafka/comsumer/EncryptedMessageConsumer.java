package org.conggroup.vizard.server.service.kafka.comsumer;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.kafka.KafkaConstants;
import org.conggroup.vizard.crypto.components.VizardMessage;
import org.conggroup.vizard.server.service.message.EncryptedMessageProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncryptedMessageConsumer {

    private final EncryptedMessageProcessor encryptedMessageProcessor;

    @KafkaListener(topics = KafkaConstants.TOPIC_MESSAGE_ENCRYPTED, containerFactory = "remoteKafkaListenerContainerFactory")
    public void consume(VizardMessage message) {
        encryptedMessageProcessor.processAnotherServerMessage(message);
    }
}
