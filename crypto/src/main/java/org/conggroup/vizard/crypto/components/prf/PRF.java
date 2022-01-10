package org.conggroup.vizard.crypto.components.prf;

import org.conggroup.vizard.crypto.components.AESKey;
import org.scijava.nativelib.NativeLoader;

import java.io.IOException;

public class PRF {

    private final AESKey AESKey;
    private final Integer bytes;

    static {
        try {
            NativeLoader.loadLibrary("prf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PRF() {
        this.AESKey = new AESKey();
        this.bytes = 8;
    }

    public PRF(AESKey AESKey, Integer bytes) throws IllegalArgumentException {
        if (AESKey == null) {
            throw new IllegalArgumentException("Invalid seed");
        }
        if (bytes > 16) {
            throw new IllegalArgumentException("Number of bytes should be less or equal than 16");
        }
        this.AESKey = AESKey;
        this.bytes = bytes;
    }

    public PRFKey gen(Long input) {
        return new PRFKey(execGen(AESKey.key, input, bytes));
    }

    private static native byte[] execGen(byte[] seed, long input, int bytes);
}
