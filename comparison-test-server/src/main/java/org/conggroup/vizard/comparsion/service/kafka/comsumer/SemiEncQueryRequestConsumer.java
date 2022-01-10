package org.conggroup.vizard.comparsion.service.kafka.comsumer;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.query.Query;
import org.conggroup.vizard.comparsion.config.kafka.TestKafkaConstants;
import org.conggroup.vizard.comparsion.service.query.SemiEncDataProcessor;
import org.conggroup.vizard.comparsion.service.query.SemiEncPRFProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SemiEncQueryRequestConsumer {

    private final SemiEncDataProcessor semiEncDataProcessor;

    private final SemiEncPRFProcessor semiEncPRFProcessor;

    @KafkaListener(topics = TestKafkaConstants.TOPIC_SEMIENC_DATA_QUERY_REQUEST, containerFactory = "localKafkaListenerContainerFactory")
    public void consumeData(Query query) { semiEncDataProcessor.processData(query); }

    @KafkaListener(topics = TestKafkaConstants.TOPIC_SEMIENC_POLICY_QUERY_REQUEST, containerFactory = "localKafkaListenerContainerFactory")
    public void consumePolicy(Query query) { semiEncPRFProcessor.processPRF(query); }

}
