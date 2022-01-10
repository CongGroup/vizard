package org.conggroup.vizard.dataconsumer.ringer;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.pedersen.Commitment;
import org.conggroup.vizard.crypto.components.pedersen.Pedersen;
import org.conggroup.vizard.crypto.components.secretsharing.AdditiveSecretSharing;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RingerProcessor {

    private final Pedersen pedersen;

    private final RingerProducer ringerProducer;

    public void produce(Integer ownerId, Long queryId, BigInteger data) {
        Commitment tmp = pedersen.commit(ownerId, queryId, data);
        ArrayList<BigInteger> rs = AdditiveSecretSharing.gen(tmp.r);
        Commitment comm0 = new Commitment(ownerId, queryId, data, rs.get(0));
        Commitment comm1 = new Commitment(ownerId, queryId, data, rs.get(1));
        ringerProducer.produce(queryId, comm0, comm1);
    }

}
