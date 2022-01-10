package org.conggroup.vizard.dataconsumer.ringer;

import org.conggroup.vizard.crypto.components.pedersen.Commitment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RingerProducer {

    @Autowired
    @Qualifier("KafkaProducerZero")
    private KafkaTemplate<String, Object> kafkaProducerZero;

    @Autowired
    @Qualifier("KafkaProducerOne")
    private KafkaTemplate<String, Object> kafkaProducerOne;

    public void produce(Long queryId, Commitment comm0, Commitment comm1) {
        String topicName = "vizard.ringer.commitment.raw-data";
        kafkaProducerZero.send(topicName, queryId.toString(), comm0);
        kafkaProducerOne.send(topicName, queryId.toString(), comm1);
    }
}
