package org.conggroup.vizard.server.config;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.beaver.Beaver;
import org.conggroup.vizard.crypto.components.beaver.BeaverTriplet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class BeaverProvider {

    private final Environment environment;

    @Bean
    public Beaver beaver() {
        Integer serverNo = environment.getProperty("vizard.server.serverNo", Integer.class);
        String a = environment.getProperty("vizard.crypto.beaver.a");
        String b = environment.getProperty("vizard.crypto.beaver.b");
        String c = environment.getProperty("vizard.crypto.beaver.c");
        BeaverTriplet beaverTriplet = new BeaverTriplet(a, b, c);
        return new Beaver(serverNo, beaverTriplet);
    }
}
