package org.conggroup.vizard.resultreleasecontrol.config.kafka;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.kafka.KafkaConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class KafkaConsumerProvider {

    private final Environment environment;

    public Map<String, Object> consumerConfigs(Integer serverNo) {
        String bootstrapServer = environment.getProperty(String.format("%s.%d", KafkaConstants.BOOTSTRAP_SERVER_PREFIX, serverNo));
        return KafkaConsumerConfigTemplate.GenerateConfig(bootstrapServer, "vizard-group");
    }

    @Bean(name="KafkaConsumerZeroFactory")
    public ConsumerFactory<String, Object> consumerZeroFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(0));
    }

    @Bean(name="KafkaConsumerOneFactory")
    public ConsumerFactory<String, Object> consumerOneFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(1));
    }

    @Bean(name="zeroListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Object> zeroListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerZeroFactory());
        return factory;
    }

    @Bean(name="oneListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Object> oneListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerOneFactory());
        return factory;
    }

}
