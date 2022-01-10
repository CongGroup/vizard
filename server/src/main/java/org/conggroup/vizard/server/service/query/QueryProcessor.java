package org.conggroup.vizard.server.service.query;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.cipher.VizardCipher;
import org.conggroup.vizard.crypto.components.beaver.BeaverDiff;
import org.conggroup.vizard.crypto.components.dpf.DPFKey;
import org.conggroup.vizard.crypto.components.query.Query;
import org.conggroup.vizard.crypto.components.query.QuerySecrets;
import org.conggroup.vizard.crypto.components.VizardMessage;
import org.conggroup.vizard.server.service.dpf.DPFProcessor;
import org.conggroup.vizard.server.service.kafka.producer.BeaverDiffProducer;
import org.conggroup.vizard.server.service.kafka.producer.QueryResultProducer;
import org.conggroup.vizard.server.service.persistence.FinalMessagePersistenceUtil;
import org.conggroup.vizard.server.service.sss.SSSProcessor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class QueryProcessor {

    private final VizardCipher vizardCipher;

    private final DPFProcessor dpfProcessor;

    private final BeaverDiffProducer beaverDiffProducer;

    private final QueryResultProducer queryResultProducer;

    private final FinalMessagePersistenceUtil finalMessagePersistenceUtil;

    private final SSSProcessor sssProcessor;

    private final ConcurrentHashMap<Long, ArrayList<VizardMessage>> Cb_map = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<Long, BeaverDiff> beaverDiff_map = new ConcurrentHashMap<>();

    public void process(Query query) throws RuntimeException{
        if(!Cb_map.containsKey(query.queryId)) {
            Cb_map.put(query.queryId, new ArrayList<VizardMessage>());
        }
        HashMap<Integer, ArrayList<VizardMessage>> records = finalMessagePersistenceUtil.searchRecords(query.beginSeqIndex, query.endSeqIndex);
        HashMap<Integer, ArrayList<DPFKey>> dpfKeyMap = dpfProcessor.loadDPFKeys();
        ArrayList<BigInteger> T_b = new ArrayList<BigInteger>();

        for (Integer ownerId: records.keySet()) {
            ArrayList<VizardMessage> messages = records.get(ownerId);
            //C_bi
            ArrayList<DPFKey> keys = dpfKeyMap.get(ownerId);
            BigInteger T_bi = dpfProcessor.eval(query.description, ownerId, keys);
            VizardMessage C_bi = vizardCipher.C_bi(T_bi, messages);
            Cb_map.get(query.queryId).add(C_bi);
            //Tb
            T_b.add(T_bi);
        }

        Map.Entry<Integer, ArrayList<VizardMessage>> firstEntry = records.entrySet().iterator().next();
        ArrayList<VizardMessage> messages = firstEntry.getValue();

        //K_b
        VizardMessage firstMessage = messages.get(0);
        VizardMessage lastMessage = messages.get(messages.size()-1);
        Long beginSeqIndexMinusOne = firstMessage.seqIndex-1;
        Long endSeqIndex = lastMessage.seqIndex;
        BigInteger K_b = vizardCipher.K_b(beginSeqIndexMinusOne, endSeqIndex);

        //T_b
        BigInteger T_b_sum = vizardCipher.sumT_b(T_b);
        BeaverDiff beaverDiff = vizardCipher.generateBeaverDiff(query.queryId, T_b_sum, K_b);

        //store and produce beaverDiff
        beaverDiff_map.put(query.queryId, beaverDiff);
        beaverDiffProducer.produce(query.queryId, beaverDiff);
    }

    public void processBeaverDiff(BeaverDiff receivedBeaverDiff) {
        Long queryId = receivedBeaverDiff.queryId;
        //wait for local beaver diff
        while(!beaverDiff_map.containsKey(queryId));

        BeaverDiff localBeaverDiff = beaverDiff_map.get(queryId);
        //D_0i+D_1i = (T_0i+T_1i)*(K_0i+K_1i) = (T_0i+T_1i)*[(g_0_(j-1) - g_0_l)+(g_1_(j-1) - g_1_l)]
        BigInteger D_b = vizardCipher.D_b(localBeaverDiff, receivedBeaverDiff);

        //Cb = sum(C_bi) = sum(T_bi*C_i_star)
        BigInteger C_b = vizardCipher.sumMessages(Cb_map.get(queryId)).c;

        BigInteger result = vizardCipher.add(C_b, D_b);
        ArrayList<String> sharedSecrets = sssProcessor.genSecretShares(result);
        QuerySecrets querySecrets = new QuerySecrets(queryId, sharedSecrets);
        queryResultProducer.produce(querySecrets);

        releaseMemory(queryId);
    }

    private void releaseMemory(Long queryId) {
        Cb_map.get(queryId).clear();
        Cb_map.remove(queryId);
        beaverDiff_map.remove(queryId);
    }

}
