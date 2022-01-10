package org.conggroup.vizard.crypto.components.operator;

import java.math.BigInteger;
import java.util.ArrayList;

public class Operator {

    private final BigInteger M;

    public Operator(BigInteger M) {
        this.M = M;
    }

    public Operator(Integer mBits) {
        M = BigInteger.ONE.shiftLeft(mBits);
    }

    public BigInteger getM() {
        return M;
    }

    public Integer getMBits() {
        return M.bitLength() - 1;
    }

    public BigInteger add(BigInteger lhs, BigInteger rhs) {
        return lhs.add(rhs).mod(M);
    }

    public BigInteger subtract(BigInteger lhs, BigInteger rhs) { return lhs.subtract(rhs).mod(M); }

    public BigInteger multiply(BigInteger lhs, BigInteger rhs) {
        return lhs.multiply(rhs).mod(M);
    }

    public BigInteger modM(BigInteger lhs) {
        return lhs.mod(M);
    }

    public BigInteger sum(ArrayList<BigInteger> items) {
        BigInteger res = BigInteger.ZERO;
        for(BigInteger i: items) {
            res = res.add(i);
        }
        return res.mod(M);
    }

    public BigInteger encrypt(BigInteger data, BigInteger key1, BigInteger key2) {
        return data.add(key1).subtract(key2).mod(M);
    }

    public BigInteger decrypt(BigInteger ciphertext, BigInteger key1, BigInteger key2) {
        return ciphertext.subtract(key1).add(key2).mod(M);
    }

}
