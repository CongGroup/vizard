package org.conggroup.vizard.crypto.components.secretsharing;

import org.conggroup.vizard.crypto.utils.RandomGenerator;

import java.math.BigInteger;
import java.util.ArrayList;

public class AdditiveSecretSharing {

    public static ArrayList<BigInteger> gen(BigInteger data) {
        ArrayList<BigInteger> res = new ArrayList<BigInteger>(2);
        BigInteger rndBigInt = RandomGenerator.randomBigIntegerWithUpperLimit(BigInteger.ONE.shiftLeft(64));
        BigInteger less = data.subtract(rndBigInt);
        res.add(rndBigInt);
        res.add(less);
        return res;
    }

    public static BigInteger recover(ArrayList<BigInteger> secrets) {
        BigInteger sum = BigInteger.ZERO;
        for(BigInteger n: secrets) {
            sum = sum.add(n);
        }
        return sum;
    }

}
