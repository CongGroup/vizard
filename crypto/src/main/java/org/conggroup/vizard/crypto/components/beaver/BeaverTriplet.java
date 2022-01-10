package org.conggroup.vizard.crypto.components.beaver;

import lombok.Data;

import java.math.BigInteger;

@Data
public class BeaverTriplet {

    public BigInteger a;
    public BigInteger b;
    public BigInteger c;

    public BeaverTriplet(BigInteger a, BigInteger b, BigInteger c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public BeaverTriplet(String a, String b, String c) {
        this.a = new BigInteger(a);
        this.b = new BigInteger(b);
        this.c = new BigInteger(c);
    }
}
