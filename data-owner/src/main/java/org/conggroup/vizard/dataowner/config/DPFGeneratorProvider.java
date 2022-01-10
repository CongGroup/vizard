package org.conggroup.vizard.dataowner.config;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.AESKey;
import org.conggroup.vizard.crypto.components.dpf.DPFGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class DPFGeneratorProvider {

    private final Environment environment;

    @Bean
    public DPFGenerator dpfClient() {
        String aesKeyStrL = environment.getProperty("vizard.crypto.dpf.aesKeyL");
        String aesKeyStrR = environment.getProperty("vizard.crypto.dpf.aesKeyR");
        AESKey aesKeyL = new AESKey(aesKeyStrL);
        AESKey aesKeyR = new AESKey(aesKeyStrR);
        return new DPFGenerator(aesKeyL, aesKeyR);
    }
}
