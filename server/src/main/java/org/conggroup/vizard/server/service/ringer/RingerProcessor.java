package org.conggroup.vizard.server.service.ringer;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.pedersen.Commitment;
import org.conggroup.vizard.crypto.components.pedersen.Pedersen;
import org.conggroup.vizard.server.service.ringer.kafka.CommitmentProducer;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RingerProcessor {

    private final Pedersen pedersen;

    private final CommitmentProducer commitmentProducer;

    private final ConcurrentHashMap<Long, Commitment> commitmentMap = new ConcurrentHashMap<>();

    public void processRawData(Commitment _commitment) {
        Commitment commitment = pedersen.commit(0, _commitment.queryId, _commitment.c, _commitment.r);
        commitmentMap.put(commitment.queryId, commitment);
        commitmentProducer.produce(commitment.queryId, commitment);
    }

    public Commitment processCommitment(Commitment receivedCommitment) {
        while (!commitmentMap.containsKey(receivedCommitment.queryId));
        Commitment localCommitment = commitmentMap.get(receivedCommitment.queryId);
        Commitment res = pedersen.add(localCommitment, receivedCommitment);
        commitmentMap.remove(receivedCommitment.queryId);
        return res;
    }

}
