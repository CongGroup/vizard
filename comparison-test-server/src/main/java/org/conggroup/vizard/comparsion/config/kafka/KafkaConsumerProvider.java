package org.conggroup.vizard.comparsion.config.kafka;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.kafka.KafkaConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class KafkaConsumerProvider {

    private final Environment environment;

    public Map<String, Object> localConsumerConfigs() {
        Integer serverNo = environment.getProperty("vizard.server.serverNo", Integer.class);
        String bootstrapServer = environment.getProperty(String.format("%s.%d", KafkaConstants.BOOTSTRAP_SERVER_PREFIX, serverNo));
        return KafkaConsumerConfigTemplate.GenerateConfig(bootstrapServer, "vizard-group");
    }

    public Map<String, Object> remoteConsumerConfigs() {
        Integer serverNo = environment.getProperty("vizard.server.serverNo", Integer.class);
        String bootstrapServer = environment.getProperty(String.format("%s.%d", KafkaConstants.BOOTSTRAP_SERVER_PREFIX, (1-serverNo)));
        return KafkaConsumerConfigTemplate.GenerateConfig(bootstrapServer, "vizard-group");
    }

    @Bean(name="KafkaLocalConsumerFactory")
    public ConsumerFactory<String, Object> localConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(localConsumerConfigs());
    }

    @Bean(name="localKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Object> localKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(localConsumerFactory());
        return factory;
    }

    @Bean(name="KafkaRemoteConsumerFactory")
    public ConsumerFactory<String, Object> remoteConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(remoteConsumerConfigs());
    }

    @Bean(name="remoteKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Object> remoteKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(remoteConsumerFactory());
        return factory;
    }

}
