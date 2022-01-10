package org.conggroup.vizard.server.service.kafka.producer;

import org.conggroup.vizard.crypto.components.kafka.KafkaConstants;
import org.conggroup.vizard.crypto.components.query.QuerySecrets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class QueryResultProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaProducer;

    public void produce(QuerySecrets result) {
        String topicName = KafkaConstants.TOPIC_QUERY_RESULT;
        kafkaProducer.send(topicName, result.queryId.toString(), result);
    }
}
