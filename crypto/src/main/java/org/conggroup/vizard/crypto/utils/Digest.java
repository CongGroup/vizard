package org.conggroup.vizard.crypto.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Digest {

    public static byte[] md5(String inputStr) {
        byte[] hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            hash = md.digest(inputStr.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static long crc32(String inputStr) {
        byte[] bytes = inputStr.getBytes();
        Checksum checksum = new CRC32();
        checksum.update(bytes);
        return checksum.getValue();
    }
}
