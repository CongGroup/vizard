package org.conggroup.vizard.server.service.sss;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.utils.Converter;
import org.conggroup.vizard.crypto.components.secretsharing.ShamirSecretSharing;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class SSSProcessor {

    private final ShamirSecretSharing shamirSecretSharing;

    public ArrayList<String> genSecretShares(BigInteger res) {
        byte[] bytes = Converter.bigIntegerToBytes(res);
        return shamirSecretSharing.split(bytes);
    }

}
