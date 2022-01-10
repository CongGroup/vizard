package org.conggroup.vizard.crypto.utils.serializer;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.math.BigInteger;

public class BigIntegerSerializer extends StdConverter<BigInteger, String> {

    @Override
    public String convert(BigInteger bigInteger) {
        return bigInteger.toString();
    }
}
