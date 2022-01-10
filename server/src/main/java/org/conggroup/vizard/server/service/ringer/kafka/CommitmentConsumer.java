package org.conggroup.vizard.server.service.ringer.kafka;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.pedersen.Commitment;
import org.conggroup.vizard.server.service.ringer.RingerProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommitmentConsumer {

    private final RingerProcessor ringerProcessor;

    @KafkaListener(topics = "vizard.ringer.commitment.raw-data", containerFactory = "localKafkaListenerContainerFactory")
    public void consumeRawData(Commitment commitment) {
        ringerProcessor.processRawData(commitment);
    }

    @KafkaListener(topics = "vizard.ringer.commitment.server", containerFactory = "localKafkaListenerContainerFactory")
    public void consumeCommitment(Commitment commitment) {
        ringerProcessor.processCommitment(commitment);
    }

}
