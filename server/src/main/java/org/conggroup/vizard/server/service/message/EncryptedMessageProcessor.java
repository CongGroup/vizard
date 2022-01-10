package org.conggroup.vizard.server.service.message;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.cipher.VizardCipher;
import org.conggroup.vizard.crypto.components.VizardMessage;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@RequiredArgsConstructor
public class EncryptedMessageProcessor {

    private final VizardCipher vizardCipher;

    private final FinalMessageProcessor finalMessageProcessor;

    private final ConcurrentHashMap<Integer, LinkedBlockingQueue<VizardMessage>> queueMap = new ConcurrentHashMap<>();

    public void addToQueue(VizardMessage message) {
        if(!queueMap.containsKey(message.ownerId)) {
            queueMap.put(message.ownerId, new LinkedBlockingQueue<VizardMessage>());
        }
        try {
            queueMap.get(message.ownerId).put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void processAnotherServerMessage(VizardMessage receivedMessage) throws RuntimeException {
        //Assume the local share of message always exists
        try {
            //Waiting for message generation
            while (!queueMap.containsKey(receivedMessage.ownerId));
            VizardMessage localMessage = queueMap.get(receivedMessage.ownerId).take();
            if (!Objects.equals(localMessage.seqIndex, receivedMessage.seqIndex)) {
                throw new RuntimeException("seqIndex mismatch!");
            }
            VizardMessage finalMessage = vizardCipher.add(localMessage, receivedMessage);
            finalMessageProcessor.process(finalMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public VizardMessage processMessageBenchmark(VizardMessage receivedMessage) throws RuntimeException {
        //Assume the local share of message always exists
        try {
            VizardMessage localMessage = queueMap.get(receivedMessage.ownerId).take();
            if (!Objects.equals(localMessage.seqIndex, receivedMessage.seqIndex)) {
                throw new RuntimeException("seqIndex mismatch!");
            }
            return vizardCipher.add(localMessage, receivedMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
