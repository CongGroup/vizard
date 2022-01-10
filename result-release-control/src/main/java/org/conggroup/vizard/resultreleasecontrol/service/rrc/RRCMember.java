package org.conggroup.vizard.resultreleasecontrol.service.rrc;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class RRCMember {

    private final ConcurrentHashMap<Long, String> sharedSecretZero = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<Long, String> sharedSecretOne = new ConcurrentHashMap<>();

    public void receive(Long queryId, String secret, Integer no) {
        if(no==0) {
            sharedSecretZero.put(queryId, secret);
        }else {
            sharedSecretOne.put(queryId, secret);
        }
    }

    public ArrayList<String> send(Long queryId) {
        ArrayList<String> res = new ArrayList<String>(2);
        res.add(sharedSecretZero.get(queryId));
        sharedSecretZero.remove(queryId);
        res.add(sharedSecretOne.get(queryId));
        sharedSecretOne.remove(queryId);
        return res;
    }

}
