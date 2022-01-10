package org.conggroup.vizard.server.service.message;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.cipher.VizardCipher;
import org.conggroup.vizard.crypto.components.VizardMessage;
import org.conggroup.vizard.server.service.kafka.producer.EncryptedMessageProducer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RawMessageProcessor {

    private final VizardCipher vizardCipher;

    private final EncryptedMessageProducer encryptedMessageProducer;

    private final EncryptedMessageProcessor encryptedMessageProcessor;

    public void process(VizardMessage rawMessage) {
        VizardMessage encryptedMessage = vizardCipher.encrypt(rawMessage, rawMessage.seqIndex-1);

        encryptedMessageProcessor.addToQueue(encryptedMessage);
        encryptedMessageProducer.produce(encryptedMessage);
    }

    public VizardMessage encOnlyBenchmark(VizardMessage rawMessage) {
        return vizardCipher.encrypt(rawMessage, rawMessage.seqIndex-1);
    }
}
