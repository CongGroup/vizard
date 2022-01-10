package org.conggroup.vizard.crypto.components.pedersen;

import org.conggroup.vizard.crypto.utils.RandomGenerator;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Pedersen {

    public BigInteger p;
    public BigInteger q;
    public BigInteger g;
    public BigInteger y;

    public Pedersen(BigInteger p, BigInteger q, BigInteger g, BigInteger y) {
        this.p = p;
        this.q = q;
        this.g = g;
        this.y = y;
    }

    public static Pedersen getInstance(Integer mBits) {
        SecureRandom random = new SecureRandom();

        BigInteger q;
        BigInteger p;
        BigInteger g;
        BigInteger y;

        do {
            q = BigInteger.probablePrime(mBits, random);
            p = q.multiply(BigInteger.TWO).add(BigInteger.ONE);
        } while (!p.isProbablePrime(1));

        //g = h ** 2 mod p
        BigInteger h = RandomGenerator.randomBigIntegerWithBothLimit(BigInteger.TWO, p.subtract(BigInteger.TWO));
        g = h.modPow(BigInteger.TWO, p);

        //y = g ** x
        BigInteger x = RandomGenerator.randomBigIntegerWithBothLimit(BigInteger.TWO, q.subtract(BigInteger.TWO));
        y = g.modPow(x, p);

        return new Pedersen(p,q,g,y);
    }

    public Commitment commit(Integer ownerId, Long queryId, BigInteger m) {
        BigInteger r = RandomGenerator.randomBigIntegerWithBothLimit(
                BigInteger.TWO, q.subtract(BigInteger.TWO));
        return commit(ownerId, queryId, m, r);
    }

    public Commitment commit(Integer ownerId, Long queryId, BigInteger m, BigInteger r) {
        //c = (g**m)*(y**r)
        BigInteger g_m = g.modPow(m, p);
        BigInteger y_r = y.modPow(r, p);
        BigInteger c = g_m.multiply(y_r).mod(p);
        return new Commitment(ownerId, queryId, c, r);
    }

    public Commitment add(Commitment lhs, Commitment rhs) {
        BigInteger c = lhs.c.multiply(rhs.c).mod(p);
        BigInteger r = lhs.r.add(rhs.r);
        return new Commitment(0, lhs.queryId, c, r);
    }

    public Commitment sum(Long queryId, ArrayList<Commitment> commitments) {
        Commitment head = commitments.get(0);
        BigInteger c = head.c;
        BigInteger r = head.r;

        for(int i=1; i<commitments.size(); i++) {
            Commitment commitment = commitments.get(i);
            c = c.multiply(commitment.c).mod(p);
            r = r.add(commitment.r);
        }

        return new Commitment(0, queryId, c, r);
    }

    public Boolean verify(BigInteger m, Commitment c) {
        BigInteger g_m = g.modPow(m, p);
        BigInteger y_r = y.modPow(c.r, p);
        BigInteger tmp = g_m.multiply(y_r).mod(p);
        return c.c.equals(tmp);
    }

}
