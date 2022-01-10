package org.conggroup.vizard.crypto.components.dpf;

import org.conggroup.vizard.crypto.components.AESKey;
import org.scijava.nativelib.NativeLoader;

import java.io.IOException;

public abstract class DPF {

    AESKey aesKeyL;
    AESKey aesKeyR;

    static {
        try {
            NativeLoader.loadLibrary("dpf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static native byte[] execGen(long hash, byte[] aesKeyL, byte[] aesKeyR);

    static native int execEval(long hash, byte[] aesKeyL, byte[] aesKeyR, byte[] key, int serverNo);
}
