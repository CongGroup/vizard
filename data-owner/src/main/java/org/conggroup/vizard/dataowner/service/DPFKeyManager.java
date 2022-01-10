package org.conggroup.vizard.dataowner.service;

import org.conggroup.vizard.crypto.components.dpf.DPFGenerator;
import org.conggroup.vizard.crypto.components.dpf.DPFHash;
import org.conggroup.vizard.crypto.components.dpf.DPFKey;
import org.conggroup.vizard.crypto.components.policy.Policy;
import org.conggroup.vizard.crypto.utils.Digest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

@Service
public class DPFKeyManager extends DPFHash {

    @Autowired
    private DPFGenerator dpfGenerator;

    public ArrayList<DPFKey> genKeys(Policy policy, Integer keyIndex, String description) {
        long digest = Digest.crc32(description);
        return dpfGenerator.gen(policy.ownerId, keyIndex, digest, policy.conditionType, policy.not);
    }

    public ArrayList<ArrayList<DPFKey>> genHashedDPFKeys(Policy policy) {
        int m = (int) Math.ceil(1.5 * policy.conditions.length);
        HashMap<Integer, String> hashMap = new HashMap<>(m);
        for(String condition: policy.conditions) {
            tryInsert(condition, hashMap, m);
        }
        ArrayList<ArrayList<DPFKey>> res = new ArrayList<ArrayList<DPFKey>>(m);
        for(int i=0; i<m; i++) {
            long digest = -1;
            if(hashMap.containsKey(i)) {
                digest = Digest.crc32(hashMap.get(i));
            }
            ArrayList<DPFKey> dpfKeys = dpfGenerator.gen(policy.ownerId, i, digest, policy.conditionType, policy.not);
            res.add(dpfKeys);
        }
        return res;
    }

    private Boolean tryInsert(String condition, HashMap<Integer, String> hashMap, Integer m) {
        ArrayList<Integer> indexList = new ArrayList<>(Arrays.asList(0,1,2));

        int count = 0;
        String conditionToInsert = condition;

        while (count<m) {
            Collections.shuffle(indexList);

            int i = 0;
            while (i<3) {
                int candidateIndex = calcIndex(conditionToInsert, indexList.get(i), m);
                if(!hashMap.containsKey(candidateIndex)) {
                    hashMap.put(candidateIndex, conditionToInsert);
                    return true;
                }
                i++;
            }

            //All candidates fail, using CuckooHash policy
            int targetIndex = calcIndex(conditionToInsert, indexList.get(2), m);
            String originalCondition = hashMap.get(targetIndex);
            hashMap.put(targetIndex, conditionToInsert);
            conditionToInsert = originalCondition;
            count++;
        }

        return false;
    }
}
