package org.conggroup.vizard.comparsion.service.persistence;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.policy.Policy;
import org.conggroup.vizard.comparsion.dao.PolicyEntityRepository;
import org.conggroup.vizard.comparsion.entity.PolicyEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PolicyPersistenceUtil {

    private final PolicyEntityRepository policyEntityRepository;

    public void saveEntities(ArrayList<PolicyEntity> entities) {
        policyEntityRepository.saveAll(entities);
    }

    public void saveEntity(PolicyEntity entity) {
        policyEntityRepository.save(entity);
    }

    public void saveAll(ArrayList<Policy> policies) {
        List<PolicyEntity> entities = policies.stream()
                .map(PolicyEntity::new)
                .collect(Collectors.toList());
        policyEntityRepository.saveAll(entities);
    }

    public void save(Policy policy) {
        PolicyEntity tmp = new PolicyEntity(policy);
        policyEntityRepository.save(tmp);
    }

    public HashMap<Integer, Policy> loadAll() {
        List<PolicyEntity> entities = policyEntityRepository.findAll();
        HashMap<Integer, Policy> map = new HashMap<>();
        for(PolicyEntity e: entities) {
            map.put(e.getOwnerId(), e.policy());
        }
        return map;
    }

    public Policy load(Integer ownerId) {
        PolicyEntity tmp = policyEntityRepository.findOneByOwnerId(ownerId);
        return tmp.policy();
    }
}
