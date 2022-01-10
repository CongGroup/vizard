package org.conggroup.vizard.crypto.components.prf;

import lombok.Data;

import java.math.BigInteger;

@Data
public class PRFKey {

    public BigInteger key;

    public PRFKey(BigInteger value) {
        this.key = value;
    }

    public PRFKey(byte[] value) {
        this.key = new BigInteger(1, value);
    }

}
