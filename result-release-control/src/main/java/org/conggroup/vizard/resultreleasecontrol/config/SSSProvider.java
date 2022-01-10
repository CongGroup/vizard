package org.conggroup.vizard.resultreleasecontrol.config;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.secretsharing.ShamirSecretSharing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class SSSProvider {

    private final Environment environment;

    @Bean
    public ShamirSecretSharing shamirSecretSharing() {
        Integer totalParts = environment.getProperty("vizard.rrc.num-of-members", Integer.class);
        Float requiredPartsRatio = environment.getProperty("vizard.crypto.sss.requiredPartsRatio", Float.class);
        return new ShamirSecretSharing(totalParts, requiredPartsRatio);
    }
}
