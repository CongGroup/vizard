package org.conggroup.vizard.dataconsumer.config;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.dataconsumer.utils.SnowFlakeIdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class SnowFlakeIdProvider {

    private final Environment environment;

    @Bean
    public SnowFlakeIdUtil snowFlakeIdUtil() {
        return new SnowFlakeIdUtil(0L);
    }
}
