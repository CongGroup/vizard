package org.conggroup.vizard.crypto.components.beaver;

import org.conggroup.vizard.crypto.components.secretsharing.AdditiveSecretSharing;
import org.conggroup.vizard.crypto.utils.RandomGenerator;

import java.math.BigInteger;
import java.util.ArrayList;

public class TripletGenerator {

    private final BigInteger M;

    public TripletGenerator(BigInteger M) {
        this.M = M;
    }

    public ArrayList<BeaverTriplet> generateTriplets() {
        BigInteger a = RandomGenerator.randomBigIntegerWithUpperLimit(M);
        BigInteger b = RandomGenerator.randomBigIntegerWithUpperLimit(M);
        BigInteger c = a.multiply(b);

        ArrayList<BigInteger> aShares = AdditiveSecretSharing.gen(a);
        ArrayList<BigInteger> bShares = AdditiveSecretSharing.gen(b);
        ArrayList<BigInteger> cShares = AdditiveSecretSharing.gen(c);

        ArrayList<BeaverTriplet> res = new ArrayList<BeaverTriplet>(2);
        for(int i=0; i< 2; i++) {
            res.add(new BeaverTriplet(aShares.get(i), bShares.get(i), cShares.get(i)));
        }
        return res;
    }

}
