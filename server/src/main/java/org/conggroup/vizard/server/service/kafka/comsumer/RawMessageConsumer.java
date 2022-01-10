package org.conggroup.vizard.server.service.kafka.comsumer;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.kafka.KafkaConstants;
import org.conggroup.vizard.crypto.components.VizardMessage;
import org.conggroup.vizard.server.service.message.RawMessageProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RawMessageConsumer {

    private final RawMessageProcessor rawMessageProcessor;

    @KafkaListener(topics = KafkaConstants.TOPIC_MESSAGE_RAW, containerFactory = "localKafkaListenerContainerFactory")
    public void consume(VizardMessage message) {
        rawMessageProcessor.process(message);
    }
}
