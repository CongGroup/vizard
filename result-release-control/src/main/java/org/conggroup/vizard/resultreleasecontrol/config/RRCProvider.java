package org.conggroup.vizard.resultreleasecontrol.config;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.operator.Operator;
import org.conggroup.vizard.crypto.components.secretsharing.ShamirSecretSharing;
import org.conggroup.vizard.resultreleasecontrol.service.rrc.RRC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class RRCProvider {

    private final Environment environment;

    private final ShamirSecretSharing shamirSecretSharing;

    private final Operator operator;

    @Bean
    public RRC rrc() {
        Integer n_members = environment.getProperty("vizard.rrc.num-of-members", Integer.class);
        return new RRC(n_members, shamirSecretSharing, operator);
    }
}
