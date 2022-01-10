package org.conggroup.vizard.dataconsumer.service;

import org.conggroup.vizard.crypto.components.kafka.KafkaConstants;
import org.conggroup.vizard.crypto.components.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class QueryRequestProducer {

    @Autowired
    @Qualifier("KafkaProducerZero")
    private KafkaTemplate<String, Object> kafkaProducerZero;

    @Autowired
    @Qualifier("KafkaProducerOne")
    private KafkaTemplate<String, Object> kafkaProducerOne;

    public void produce(Query query) {
        kafkaProducerZero.send(KafkaConstants.TOPIC_QUERY_REQUEST, query);
        kafkaProducerOne.send(KafkaConstants.TOPIC_QUERY_REQUEST, query);
    }

}
