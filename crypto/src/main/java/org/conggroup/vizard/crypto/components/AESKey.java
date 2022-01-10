package org.conggroup.vizard.crypto.components;

import lombok.Data;
import org.conggroup.vizard.crypto.utils.Digest;

@Data
public class AESKey {

    public byte[] key;

    public AESKey() {
        this.key = Digest.md5("test aes key");
    }

    public AESKey(byte[] seed) throws IllegalArgumentException {
        if (seed == null || seed.length != 16) {
            throw new IllegalArgumentException("Invalid seed");
        }
        this.key = seed;
    }

    public AESKey(String strSeed) {
        this.key = Digest.md5(strSeed);
    }
}
