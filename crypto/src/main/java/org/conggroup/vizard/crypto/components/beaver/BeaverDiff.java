package org.conggroup.vizard.crypto.components.beaver;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.conggroup.vizard.crypto.utils.deserializer.BigIntegerDeserializer;
import org.conggroup.vizard.crypto.utils.serializer.BigIntegerSerializer;

import java.math.BigInteger;

@Data
public class BeaverDiff {

    public Long queryId;

    public Integer serverNo;
    @JsonSerialize(converter = BigIntegerSerializer.class)
    @JsonDeserialize(converter = BigIntegerDeserializer.class)
    public BigInteger u;
    @JsonSerialize(converter = BigIntegerSerializer.class)
    @JsonDeserialize(converter = BigIntegerDeserializer.class)
    public BigInteger v;

    public BeaverDiff() {
        queryId = -1L;
        serverNo = 0;
        u = BigInteger.ZERO;
        v = BigInteger.ZERO;
    }

    public BeaverDiff(Long queryId, Integer serverNo, BigInteger u, BigInteger v) {
        this.queryId = queryId;
        this.serverNo = serverNo;
        this.u = u;
        this.v = v;
    }

}
