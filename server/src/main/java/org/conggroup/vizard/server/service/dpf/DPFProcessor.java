package org.conggroup.vizard.server.service.dpf;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.dpf.DPFEvaluator;
import org.conggroup.vizard.crypto.components.dpf.DPFHash;
import org.conggroup.vizard.crypto.components.operator.Operator;
import org.conggroup.vizard.crypto.components.dpf.DPFKey;
import org.conggroup.vizard.crypto.components.policy.PolicyConditionType;
import org.conggroup.vizard.server.service.persistence.DPFKeyPersistenceUtil;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class DPFProcessor extends DPFHash {

    private final DPFKeyPersistenceUtil dpfKeyPersistenceUtil;

    private final DPFEvaluator dpfEvaluator;

    private final Operator operator;

    public HashMap<Integer, ArrayList<DPFKey>> loadDPFKeys() {
        return dpfKeyPersistenceUtil.loadAll();
    }

    public BigInteger eval(String description, Integer ownerId, ArrayList<DPFKey> tmp) {
        ArrayList<DPFKey> dpfKeys;
        if(tmp.get(0).conditionType.equals(PolicyConditionType.TYPE_OR)) {
            dpfKeys = new ArrayList<DPFKey>(3);
            for(int i=0; i<3; i++) {
                int index = calcIndex(description, i, tmp.size());
                dpfKeys.add(tmp.get(index));
            }
        }else {
            dpfKeys = tmp;
        }
        ArrayList<BigInteger> Ts = new ArrayList<BigInteger>(dpfKeys.size());
        for(DPFKey key: dpfKeys) {
            Ts.add(dpfEvaluator.eval(description, key, operator));
        }
        return operator.sum(Ts);
    }
}
