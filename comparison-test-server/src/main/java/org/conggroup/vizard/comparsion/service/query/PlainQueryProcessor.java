package org.conggroup.vizard.comparsion.service.query;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.VizardMessage;
import org.conggroup.vizard.crypto.components.operator.Operator;
import org.conggroup.vizard.crypto.components.policy.Policy;
import org.conggroup.vizard.crypto.components.query.Query;
import org.conggroup.vizard.crypto.utils.Digest;
import org.conggroup.vizard.comparsion.service.kafka.producer.QueryResultProducer;
import org.conggroup.vizard.comparsion.service.kafka.producer.TestQueryResult;
import org.conggroup.vizard.comparsion.service.persistence.MessagePersistenceUtil;
import org.conggroup.vizard.comparsion.service.persistence.PolicyPersistenceUtil;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class PlainQueryProcessor {

    private final MessagePersistenceUtil messagePersistenceUtil;

    private final PolicyPersistenceUtil policyPersistenceUtil;

    private final QueryResultProducer queryResultProducer;

    private final Operator operator;

    public void process(Query query) {
        HashMap<Integer, ArrayList<VizardMessage>> records = messagePersistenceUtil.searchRecords(query.beginSeqIndex, query.endSeqIndex);
        HashMap<Integer, Policy> map = policyPersistenceUtil.loadAll();
        BigInteger sum = BigInteger.ZERO;
        String crc32Desc = Long.toHexString(Digest.crc32(query.description));
        for (Integer ownerId: records.keySet()) {
            Policy policy = map.get(ownerId);
            if (policy.conditions[0].equals(crc32Desc)) {
                ArrayList<VizardMessage> messages = records.get(ownerId);
                for(VizardMessage message: messages) {
                    operator.add(sum, message.c);
                }
            }
        }

        TestQueryResult result = new TestQueryResult(query.queryId, sum);
        queryResultProducer.produceData(result);
    }

}
