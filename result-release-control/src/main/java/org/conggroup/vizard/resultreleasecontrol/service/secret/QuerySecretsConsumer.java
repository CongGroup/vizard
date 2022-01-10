package org.conggroup.vizard.resultreleasecontrol.service.secret;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.kafka.KafkaConstants;
import org.conggroup.vizard.crypto.components.query.QuerySecrets;
import org.conggroup.vizard.resultreleasecontrol.service.rrc.RRC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuerySecretsConsumer {

    private final RRC rrc;

    @KafkaListener(topics = KafkaConstants.TOPIC_QUERY_RESULT, containerFactory = "zeroListenerContainerFactory")
    public void consumeZero(QuerySecrets querySecrets) {
        rrc.receiveSecret(querySecrets, 0);
    }

    @KafkaListener(topics = KafkaConstants.TOPIC_QUERY_RESULT, containerFactory = "oneListenerContainerFactory")
    public void consumeOne(QuerySecrets querySecrets) {
        rrc.receiveSecret(querySecrets, 1);
    }

}
