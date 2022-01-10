package org.conggroup.vizard.comparsion.service.kafka.producer;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TestQueryResult {

    public Long queryId;

    public BigInteger result;

    public TestQueryResult() {
        this.queryId = 0L;
        this.result = BigInteger.ZERO;
    }

    public TestQueryResult(Long queryId, BigInteger result) {
        this.queryId = queryId;
        this.result = result;
    }
}
