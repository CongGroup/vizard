package org.conggroup.vizard.server.config;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.dpf.DPFEvaluator;
import org.conggroup.vizard.crypto.components.AESKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.math.BigInteger;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class DPFEvaluatorProvider {

    private final Environment environment;

    @Bean
    public DPFEvaluator dpfServer() {
        Integer serverNo = environment.getProperty("vizard.server.serverNo", Integer.class);
        String aesKeyStrL = environment.getProperty("vizard.crypto.dpf.aesKeyL");
        String aesKeyStrR = environment.getProperty("vizard.crypto.dpf.aesKeyR");
        BigInteger v = new BigInteger(Objects.requireNonNull(environment.getProperty("vizard.crypto.dpf.v")));
        AESKey aesKeyL = new AESKey(aesKeyStrL);
        AESKey aesKeyR = new AESKey(aesKeyStrR);
        return new DPFEvaluator(serverNo, v, aesKeyL, aesKeyR);
    }
}
