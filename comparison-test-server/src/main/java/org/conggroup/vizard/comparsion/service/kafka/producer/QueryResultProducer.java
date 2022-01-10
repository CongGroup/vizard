package org.conggroup.vizard.comparsion.service.kafka.producer;

import org.conggroup.vizard.comparsion.config.kafka.TestKafkaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class QueryResultProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaProducer;

    public void produceData(TestQueryResult result) {
        String topicName = TestKafkaConstants.TOPIC_SEMIENC_DATA_RESULT;
        kafkaProducer.send(topicName, result.queryId.toString(), result);
    }

    public void producePRF(TestQueryResult result) {
        String topicName = TestKafkaConstants.TOPIC_SEMIENC_PRF_RESULT;
        kafkaProducer.send(topicName, result.queryId.toString(), result);
    }
}
