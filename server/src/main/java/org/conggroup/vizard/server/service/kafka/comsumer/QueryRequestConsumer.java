package org.conggroup.vizard.server.service.kafka.comsumer;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.kafka.KafkaConstants;
import org.conggroup.vizard.crypto.components.query.Query;
import org.conggroup.vizard.server.service.query.QueryProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryRequestConsumer {

    private final QueryProcessor queryProcessor;

    @KafkaListener(topics = KafkaConstants.TOPIC_QUERY_REQUEST, containerFactory = "localKafkaListenerContainerFactory")
    public void consume(Query query) { queryProcessor.process(query); }

}
