package org.conggroup.vizard.crypto.utils;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Base64;

public class Converter {

    public static long bytesToLong(byte[] in) {
        ByteBuffer buffer = ByteBuffer.allocate(in.length);
        buffer.put(in);
        buffer.flip();
        return buffer.getLong();
    }

    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    public static BigInteger bytesToBigInteger(byte[] in) {
        return new BigInteger(in);
    }

    public static byte[] bigIntegerToBytes(BigInteger x) {
        return x.toByteArray();
    }

    public static byte[] stringToBytes(String value) {
        return Base64.getDecoder().decode(value);
    }

    public static String bytesToString(byte[] value) {
        return Base64.getEncoder().encodeToString(value);
    }
}
