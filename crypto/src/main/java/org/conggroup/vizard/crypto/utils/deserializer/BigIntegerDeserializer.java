package org.conggroup.vizard.crypto.utils.deserializer;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.math.BigInteger;

public class BigIntegerDeserializer extends StdConverter<String, BigInteger> {

    @Override
    public BigInteger convert(String value) {
        return new BigInteger(value);
    }

}
