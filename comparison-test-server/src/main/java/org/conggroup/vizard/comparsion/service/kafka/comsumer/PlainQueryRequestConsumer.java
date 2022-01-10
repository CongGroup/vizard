package org.conggroup.vizard.comparsion.service.kafka.comsumer;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.query.Query;
import org.conggroup.vizard.comparsion.config.kafka.TestKafkaConstants;
import org.conggroup.vizard.comparsion.service.query.PlainQueryProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlainQueryRequestConsumer {

    private final PlainQueryProcessor plainQueryProcessor;

    @KafkaListener(topics = TestKafkaConstants.TOPIC_PLAIN_QUERY_REQUEST, containerFactory = "localKafkaListenerContainerFactory")
    public void consume(Query query) { plainQueryProcessor.process(query); }

}
