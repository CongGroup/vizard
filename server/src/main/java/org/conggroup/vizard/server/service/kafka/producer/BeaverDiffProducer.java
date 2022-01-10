package org.conggroup.vizard.server.service.kafka.producer;

import org.conggroup.vizard.crypto.components.kafka.KafkaConstants;
import org.conggroup.vizard.crypto.components.beaver.BeaverDiff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BeaverDiffProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaProducer;

    public void produce(Long queryId, BeaverDiff beaverDiff) {
        String topicName = KafkaConstants.TOPIC_BEAVER_DIFF;
        kafkaProducer.send(topicName, queryId.toString(), beaverDiff);
    }
}
