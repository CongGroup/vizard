package org.conggroup.vizard.crypto.components.dpf;

import org.conggroup.vizard.crypto.components.AESKey;
import org.conggroup.vizard.crypto.components.operator.Operator;
import org.conggroup.vizard.crypto.utils.Digest;

import java.math.BigInteger;

public class DPFEvaluator extends DPF{

    private final Integer serverNo;

    private final BigInteger v;

    public DPFEvaluator(Integer serverNo) {
        this.serverNo = serverNo;
        this.v = BigInteger.ZERO;
        this.aesKeyL = new AESKey("test");
        this.aesKeyR = new AESKey("key");
    }

    public DPFEvaluator(Integer serverNo, BigInteger v, AESKey aesKeyL, AESKey aesKeyR) throws IllegalArgumentException {
        if (serverNo != 0 && serverNo != 1) {
            throw new IllegalArgumentException("Invalid Server No");
        }
        if (aesKeyL==null || aesKeyR==null) {
            throw new IllegalArgumentException("Invalid AES Key(s)");
        }
        this.serverNo = serverNo;
        this.v = v;
        this.aesKeyL = aesKeyL;
        this.aesKeyR = aesKeyR;
    }

    public BigInteger eval(String description, DPFKey key, Operator operator) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("Invalid DPF Key");
        }
        BigInteger res = BigInteger.valueOf(execEval(Digest.crc32(description), aesKeyL.key, aesKeyR.key, key.k, serverNo));
        if(key.not_flag) {
            res = operator.subtract(v, res);
        }
        return res;
    }

}
