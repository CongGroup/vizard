package org.conggroup.vizard.crypto.components.dpf;

import org.conggroup.vizard.crypto.utils.Digest;

public abstract class DPFHash {

    private static final String[] salt = {"H1r0a","7$p2w","z5Sk9"};

    public Integer calcIndex(String condition, Integer position, Integer m) {
        return (int)(Digest.crc32(salt[position]+condition) % m);
    }

}
