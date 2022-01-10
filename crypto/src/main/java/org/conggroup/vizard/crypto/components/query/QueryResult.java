package org.conggroup.vizard.crypto.components.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.conggroup.vizard.crypto.utils.deserializer.BigIntegerDeserializer;
import org.conggroup.vizard.crypto.utils.serializer.BigIntegerSerializer;

import java.math.BigInteger;

@Data
public class QueryResult {

    public Long queryId;

    @JsonSerialize(converter = BigIntegerSerializer.class)
    @JsonDeserialize(converter = BigIntegerDeserializer.class)
    public BigInteger result;

    public QueryResult() {
        this.queryId = -1L;
        this.result = BigInteger.ZERO;
    }

    public QueryResult(Long queryId, BigInteger result) {
        this.queryId = queryId;
        this.result = result;
    }
}
