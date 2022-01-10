package org.conggroup.vizard.server.config;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.prf.PRF;
import org.conggroup.vizard.crypto.components.AESKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class PRFProvider {

    private final Environment environment;

    @Bean
    public PRF prf() {
        String aesKeyStr = environment.getProperty("vizard.crypto.prf.aesKey");
        Integer bytes = environment.getProperty("vizard.crypto.prf.bytes", Integer.class);
        AESKey aesKey = new AESKey(aesKeyStr);
        return new PRF(aesKey, bytes);
    }
}
