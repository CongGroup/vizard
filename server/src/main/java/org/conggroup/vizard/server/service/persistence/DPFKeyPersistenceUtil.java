package org.conggroup.vizard.server.service.persistence;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.dpf.DPFKey;
import org.conggroup.vizard.server.dao.DPFKeyEntityRepository;
import org.conggroup.vizard.server.entity.DPFKeyEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DPFKeyPersistenceUtil {

    private final DPFKeyEntityRepository dpfKeyEntityRepository;

    public void save(DPFKey dpfKey) {
        DPFKeyEntity tmp = new DPFKeyEntity(dpfKey);
        dpfKeyEntityRepository.save(tmp);
    }

    public void delete(Integer ownerId) {
        dpfKeyEntityRepository.deleteAllByOwnerId(ownerId);
    }

    public HashMap<Integer, ArrayList<DPFKey>> loadAll() {
        List<DPFKeyEntity> tmp = dpfKeyEntityRepository.findAll();
        HashMap<Integer, ArrayList<DPFKey>> map = new HashMap<>();
        for(DPFKeyEntity e:tmp) {
            if(!map.containsKey(e.getOwnerId())) {
                map.put(e.getOwnerId(), new ArrayList<DPFKey>());
            }
            ArrayList<DPFKey> keys = map.get(e.getOwnerId());
            keys.add(e.dpfKey());
        }
        return map;
    }

    public ArrayList<DPFKey> load(Integer ownerId) {
        ArrayList<DPFKeyEntity> tmp = dpfKeyEntityRepository.findAllByOwnerId(ownerId);
        ArrayList<DPFKey> dpfKeys = new ArrayList<>(tmp.size());
        for(DPFKeyEntity e: tmp) {
            dpfKeys.add(e.dpfKey());
        }
        return dpfKeys;
    }

    public ArrayList<DPFKey> loadWithIndexes(Integer ownerId, List<Integer> keyIndexes) {
        ArrayList<DPFKeyEntity> tmp = dpfKeyEntityRepository.findAllByOwnerIdAndKeyIndexIn(ownerId, keyIndexes);
        ArrayList<DPFKey> dpfKeys = new ArrayList<>(tmp.size());
        for(DPFKeyEntity e: tmp) {
            dpfKeys.add(e.dpfKey());
        }
        return dpfKeys;
    }
}
