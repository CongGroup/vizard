package org.conggroup.vizard.dataowner.service;

import org.conggroup.vizard.crypto.components.VizardMessage;
import org.conggroup.vizard.crypto.components.kafka.KafkaConstants;
import org.conggroup.vizard.crypto.components.secretsharing.AdditiveSecretSharing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;

@Service
public class RawMessageProducer {

    @Autowired
    @Qualifier("KafkaProducerZero")
    public KafkaTemplate<String, Object> kafkaProducerZero;

    @Autowired
    @Qualifier("KafkaProducerOne")
    public KafkaTemplate<String, Object> kafkaProducerOne;

    public void produce(VizardMessage rawData) {
        ArrayList<VizardMessage> secretShares = genAdditiveSecretShares(rawData);
        kafkaProducerZero.send(KafkaConstants.TOPIC_MESSAGE_RAW, rawData.ownerId.toString(), secretShares.get(0));
        kafkaProducerOne.send(KafkaConstants.TOPIC_MESSAGE_RAW, rawData.ownerId.toString(), secretShares.get(1));
    }

    private ArrayList<VizardMessage> genAdditiveSecretShares(VizardMessage rawData) {
        ArrayList<BigInteger> res = AdditiveSecretSharing.gen(rawData.c);
        ArrayList<VizardMessage> tmp = new ArrayList<VizardMessage>(2);
        for(BigInteger data: res) {
            tmp.add(new VizardMessage(rawData.ownerId, rawData.seqIndex, data));
        }
        return tmp;
    }

}
