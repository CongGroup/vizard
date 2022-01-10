package org.conggroup.vizard.crypto.components.dpf;

import org.conggroup.vizard.crypto.components.AESKey;

import java.util.ArrayList;

public class DPFGenerator extends DPF {

    public DPFGenerator() {
        this.aesKeyL = new AESKey("test");
        this.aesKeyR = new AESKey("key");
    }

    public DPFGenerator(AESKey aesKeyL, AESKey aesKeyR) throws IllegalArgumentException {
        if (aesKeyL==null || aesKeyR==null) {
            throw new IllegalArgumentException("Invalid AES Key(s)");
        }
        this.aesKeyL = aesKeyL;
        this.aesKeyR = aesKeyR;
    }

    public ArrayList<DPFKey> gen(Integer ownerId, Integer keyIndex, long hash, String conditionType, Boolean not) {
        byte[] keys = execGen(hash, aesKeyL.key, aesKeyR.key);
        byte[] ka = new byte[keys.length/2];
        byte[] kb = new byte[keys.length/2];
        System.arraycopy(keys, 0, ka, 0, keys.length/2);
        System.arraycopy(keys, keys.length/2, kb, 0, keys.length/2);

        ArrayList<DPFKey> res = new ArrayList<DPFKey>(2);
        res.add(new DPFKey(ownerId, keyIndex, ka, conditionType, not));
        res.add(new DPFKey(ownerId, keyIndex, kb, conditionType, not));
        return res;
    }

}
