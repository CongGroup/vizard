package org.conggroup.vizard.crypto.components.secretsharing;

import org.mitre.secretsharing.Part;
import org.mitre.secretsharing.Secrets;
import org.mitre.secretsharing.codec.PartFormats;

import java.security.SecureRandom;
import java.util.ArrayList;

public class ShamirSecretSharing {

    private final Integer totalParts;

    private final Float requiredPartsRatio;

    public ShamirSecretSharing(Integer totalParts, Float requiredPartsRatio) {
        this.totalParts = totalParts;
        this.requiredPartsRatio = requiredPartsRatio;
    }

    public ArrayList<String> split(byte[] secret) {
        SecureRandom rnd = new SecureRandom();
        int requiredParts = (int) Math.ceil(totalParts*requiredPartsRatio);
        Part[] sharingParts = Secrets.split(secret, totalParts, requiredParts, rnd);
        return format(sharingParts);
    }

    public ArrayList<String> split(byte[] secret, Integer shareNum, Float shareRatio) {
        SecureRandom rnd = new SecureRandom();
        int requiredParts = (int) Math.ceil(shareNum*shareRatio);
        Part[] sharingParts = Secrets.split(secret, shareNum, requiredParts, rnd);
        return format(sharingParts);
    }

    public byte[] join(ArrayList<String> formattedParts) {
        Part[] parts = parse(formattedParts);
        return Secrets.join(parts);
    }

    private static ArrayList<String> format(Part[] parts) {
        ArrayList<String> formattedParts = new ArrayList<String>(parts.length);
        for (Part p: parts) {
            String formatted = PartFormats.currentStringFormat().format(p);
            formattedParts.add(formatted);
        }
        return formattedParts;
    }

    private static Part[] parse(ArrayList<String> formattedParts) {
        ArrayList<Part> res = new ArrayList<Part>(formattedParts.size());
        for (String formatted: formattedParts) {
            Part p = PartFormats.parse(formatted);
            res.add(p);
        }
        Part[] parts = new Part[res.size()];
        res.toArray(parts);
        return parts;
    }

}
