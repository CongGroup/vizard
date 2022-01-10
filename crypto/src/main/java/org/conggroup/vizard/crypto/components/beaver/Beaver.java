package org.conggroup.vizard.crypto.components.beaver;

import org.conggroup.vizard.crypto.components.operator.Operator;

import java.math.BigInteger;

public class Beaver {

    private final Integer serverNo;
    private final BeaverTriplet beaverTriplet;

    public Beaver(Integer serverNo, BeaverTriplet beaverTriplet) {
        this.serverNo = serverNo;
        this.beaverTriplet = beaverTriplet;
    }

    public BeaverDiff generateDiff(Long queryId, BigInteger x, BigInteger y) {
        BigInteger u = x.subtract(beaverTriplet.a);
        BigInteger v = y.subtract(beaverTriplet.b);
        return new BeaverDiff(queryId, serverNo, u, v);
    }

    public BigInteger reconstruct(BeaverDiff local, BeaverDiff received, Operator operator) {
        BigInteger u = local.u.add(received.u);
        BigInteger v = local.v.add(received.v);

        BigInteger i1;
        // xy_i = uv/2 + u*b_i + v*a_i + c_i
        // But due to the loss caused by division
        // We simply make uv only added by server0
        if(serverNo==0) {
            i1 = u.multiply(v);
        }else {
            i1 = BigInteger.ZERO;
        }
        BigInteger i2 = u.multiply(beaverTriplet.b);
        BigInteger i3 = v.multiply(beaverTriplet.a);
        BigInteger i4 = beaverTriplet.c;

        return operator.modM(i1.add(i2).add(i3).add(i4));
    }

}
