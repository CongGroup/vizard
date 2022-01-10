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
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SemiEncDataProcessor {

    private final MessagePersistenceUtil messagePersistenceUtil;

    private final PolicyPersistenceUtil policyPersistenceUtil;

    private final QueryResultProducer queryResultProducer;

    private final Operator operator;

    public void processData(Query query) {
        HashMap<Integer, Policy> policies = policyPersistenceUtil.loadAll();
        HashMap<Integer, ArrayList<VizardMessage>> records = messagePersistenceUtil.searchRecords(query.beginSeqIndex, query.endSeqIndex);

        BigInteger res = BigInteger.ZERO;
        String crc32Desc = Long.toHexString(Digest.crc32(query.description));

        for (Map.Entry<Integer, Policy> entry: policies.entrySet()) {
            Policy policy = entry.getValue();
            if (policy.conditions[0].equals(crc32Desc)) {
                ArrayList<VizardMessage> tmp = records.get(entry.getKey());
                for(VizardMessage e: tmp) {
                    operator.add(res, e.c);
                }
            }
        }
        TestQueryResult queryResult = new TestQueryResult(query.queryId, res);
        queryResultProducer.produceData(queryResult);
    }

}
