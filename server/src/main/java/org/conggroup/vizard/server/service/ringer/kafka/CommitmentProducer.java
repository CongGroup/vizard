package org.conggroup.vizard.server.service.ringer.kafka;

import org.conggroup.vizard.crypto.components.pedersen.Commitment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommitmentProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaProducer;

    public void produce(Long queryId, Commitment commitment) {
        String topicName = "vizard.ringer.commitment.server";
        kafkaProducer.send(topicName, queryId.toString(), commitment);
    }
}