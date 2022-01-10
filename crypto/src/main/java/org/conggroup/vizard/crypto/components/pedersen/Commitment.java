package org.conggroup.vizard.crypto.components.pedersen;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.conggroup.vizard.crypto.utils.deserializer.BigIntegerDeserializer;
import org.conggroup.vizard.crypto.utils.serializer.BigIntegerSerializer;

import java.math.BigInteger;


public class Commitment {

    public Integer ownerId;
    public Long queryId;

    @JsonSerialize(converter = BigIntegerSerializer.class)
    @JsonDeserialize(converter = BigIntegerDeserializer.class)
    public BigInteger c;
    @JsonSerialize(converter = BigIntegerSerializer.class)
    @JsonDeserialize(converter = BigIntegerDeserializer.class)
    public BigInteger r;

    public Commitment() {
        this.ownerId = 0;
        this.queryId = 0L;
        this.c = BigInteger.ZERO;
        this.r = BigInteger.ZERO;
    }

    public Commitment(Integer ownerId, Long queryId, BigInteger c, BigInteger r) {
        this.ownerId = ownerId;
        this.queryId = queryId;
        this.c = c;
        this.r = r;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Commitment commitment)) {
            return false;
        }

        return c.equals(commitment.c) && r.equals(commitment.r);
    }
}
