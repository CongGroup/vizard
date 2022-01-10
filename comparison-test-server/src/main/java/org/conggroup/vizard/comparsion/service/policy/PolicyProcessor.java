package org.conggroup.vizard.comparsion.service.policy;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.utils.Digest;
import org.conggroup.vizard.comparsion.entity.PolicyEntity;
import org.conggroup.vizard.comparsion.service.persistence.PolicyPersistenceUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PolicyProcessor {

    private final PolicyPersistenceUtil policyPersistenceUtil;

    public void genPolicy(Integer beginOwnerId, Integer endOwnerId, String condition) {
        ArrayList<PolicyEntity> policyEntities = new ArrayList<PolicyEntity>();
        String policy = Long.toHexString(Digest.crc32(condition));
        for(int ownerId=beginOwnerId; ownerId<=endOwnerId; ownerId++) {
            PolicyEntity entity = new PolicyEntity();
            entity.setOwnerId(ownerId);
            entity.setConditions(policy);
            entity.setConditionType("single");
            entity.setNot_flag(false);
            policyEntities.add(entity);
        }
        policyPersistenceUtil.saveEntities(policyEntities);
    }

}
