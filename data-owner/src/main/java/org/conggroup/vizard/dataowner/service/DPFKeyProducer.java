package org.conggroup.vizard.dataowner.service;

import org.conggroup.vizard.crypto.components.dpf.DPFKey;
import org.conggroup.vizard.crypto.components.kafka.KafkaConstants;
import org.conggroup.vizard.crypto.components.policy.Policy;
import org.conggroup.vizard.crypto.components.policy.PolicyConditionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DPFKeyProducer {

    @Autowired
    @Qualifier("KafkaProducerZero")
    private KafkaTemplate<String, Object> kafkaProducerZero;

    @Autowired
    @Qualifier("KafkaProducerOne")
    private KafkaTemplate<String, Object> kafkaProducerOne;

    @Autowired
    private DPFKeyManager dpfKeyManager;

    public void sendRevokeKeyMessage(Integer ownerId) {
        //Revoke Prev dpf keys
        kafkaProducerZero.send(KafkaConstants.TOPIC_POLICY_DPFKEY_REVOKE, ownerId.toString(), ownerId);
        kafkaProducerOne.send(KafkaConstants.TOPIC_POLICY_DPFKEY_REVOKE, ownerId.toString(), ownerId);
    }

    public void produceDPFKey(Policy policy) throws RuntimeException {
        switch (policy.conditionType) {
            case PolicyConditionType.TYPE_SINGLE, PolicyConditionType.TYPE_AND -> {
                String description = String.join("", policy.conditions);
                plainKeysHandler(policy, description);
            }
            case PolicyConditionType.TYPE_OR -> hashedKeysHandler(policy);
            default -> throw new RuntimeException("Invalid Condition Type");
        }
    }

    private void plainKeysHandler(Policy policy, String description) {
        ArrayList<DPFKey> dpfKeys = dpfKeyManager.genKeys(policy, 0, description);
        kafkaProducerZero.send(KafkaConstants.TOPIC_POLICY_DPFKEY, policy.ownerId.toString(), dpfKeys.get(0));
        kafkaProducerOne.send(KafkaConstants.TOPIC_POLICY_DPFKEY, policy.ownerId.toString(), dpfKeys.get(1));
    }

    private void hashedKeysHandler(Policy policy) {
        ArrayList<ArrayList<DPFKey>> dpfKeys = dpfKeyManager.genHashedDPFKeys(policy);
        for(ArrayList<DPFKey> keys: dpfKeys) {
            kafkaProducerZero.send(KafkaConstants.TOPIC_POLICY_DPFKEY, policy.ownerId.toString(), keys.get(0));
            kafkaProducerOne.send(KafkaConstants.TOPIC_POLICY_DPFKEY, policy.ownerId.toString(), keys.get(1));
        }
    }

}

