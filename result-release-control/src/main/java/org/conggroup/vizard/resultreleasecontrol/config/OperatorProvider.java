package org.conggroup.vizard.resultreleasecontrol.config;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.operator.Operator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class OperatorProvider {

    private final Environment environment;

    @Bean
    public Operator operator() {
        Integer mBits = environment.getProperty("vizard.crypto.operator.mBits", Integer.class);
        return new Operator(mBits);
    }
}
