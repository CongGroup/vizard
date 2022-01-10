package org.conggroup.vizard.server.service.kafka.comsumer;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.kafka.KafkaConstants;
import org.conggroup.vizard.crypto.components.beaver.BeaverDiff;
import org.conggroup.vizard.server.service.query.QueryProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BeaverDiffConsumer {

    private final QueryProcessor queryProcessor;

    @KafkaListener(topics = KafkaConstants.TOPIC_BEAVER_DIFF, containerFactory = "remoteKafkaListenerContainerFactory")
    public void consume(BeaverDiff beaverDiff) {
        queryProcessor.processBeaverDiff(beaverDiff);
    }
}
