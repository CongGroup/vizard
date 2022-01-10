package org.conggroup.vizard.comparsion.service.query;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.operator.Operator;
import org.conggroup.vizard.crypto.components.policy.Policy;
import org.conggroup.vizard.crypto.components.prf.PRF;
import org.conggroup.vizard.crypto.components.prf.PRFKey;
import org.conggroup.vizard.crypto.components.query.Query;
import org.conggroup.vizard.crypto.utils.Digest;
import org.conggroup.vizard.comparsion.service.kafka.producer.QueryResultProducer;
import org.conggroup.vizard.comparsion.service.kafka.producer.TestQueryResult;
import org.conggroup.vizard.comparsion.service.persistence.PolicyPersistenceUtil;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SemiEncPRFProcessor {

    private final PolicyPersistenceUtil policyPersistenceUtil;

    private final PRF prf;

    private final Operator operator;

    private final QueryResultProducer queryResultProducer;

    public void processPRF(Query query) {
        BigInteger res = BigInteger.ZERO;

        String crc32Desc = Long.toHexString(Digest.crc32(query.description));
        HashMap<Integer, Policy> map = policyPersistenceUtil.loadAll();
        for (Map.Entry<Integer, Policy> entry: map.entrySet()) {
            Policy policy = entry.getValue();
            if (policy.conditions[0].equals(crc32Desc)) {
                PRFKey prfKey1 = prf.gen(query.beginSeqIndex-1);
                PRFKey prfKey2 = prf.gen(query.endSeqIndex);
                BigInteger decryptShare = operator.subtract(prfKey1.key, prfKey2.key);
                operator.add(res, decryptShare);
            }
        }
        TestQueryResult testQueryResult = new TestQueryResult(query.queryId, res);
        queryResultProducer.producePRF(testQueryResult);
    }

}
