package org.conggroup.vizard.crypto.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomGenerator {

    public static BigInteger randomBigIntegerWithUpperLimit(BigInteger upperLimit) {
        SecureRandom rnd = new SecureRandom();
        BigInteger randomNumber;
        do {
            randomNumber = new BigInteger(upperLimit.bitLength(), rnd);
        } while (randomNumber.compareTo(upperLimit) >= 0);
        return randomNumber;
    }

    public static BigInteger randomBigIntegerWithBothLimit(BigInteger lowerLimit, BigInteger upperLimit) {
        SecureRandom rnd = new SecureRandom();
        BigInteger randomNumber;
        do {
            randomNumber = new BigInteger(upperLimit.bitLength(), rnd);
        } while (randomNumber.compareTo(upperLimit) > 0 || randomNumber.compareTo(lowerLimit) < 0);
        return randomNumber;
    }

    public static BigInteger randomBigIntegerWithModM(BigInteger M) {
        SecureRandom rnd = new SecureRandom();
        BigInteger tmp =  new BigInteger(M.bitLength(), rnd);
        return tmp.mod(M);
    }

    public static BigInteger randomGeometricDist(Integer geoSeed) {
        SecureRandom rnd = new SecureRandom();
        double p = 1.0 / ((double) geoSeed);
        long res = (long)(Math.ceil(Math.log(rnd.nextDouble())/Math.log(1.0-p)));
        return BigInteger.valueOf(res);
    }

}
