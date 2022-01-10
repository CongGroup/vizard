package org.conggroup.vizard.resultreleasecontrol.config.kafka;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.kafka.KafkaConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class KafkaProducerProvider {

    private final Environment environment;

    public Map<String, Object> producerConfigs(Integer serverNo) {
        String bootstrapServer = environment.getProperty(String.format("%s.%d", KafkaConstants.BOOTSTRAP_SERVER_PREFIX, serverNo));
        return KafkaProducerConfigTemplate.GenerateConfig(bootstrapServer);
    }

    @Bean(name="KafkaProducerZeroFactory")
    public ProducerFactory<String, Object> producerZeroFactory() {
        return new DefaultKafkaProducerFactory<String, Object>(producerConfigs(0));
    }

    @Bean(name="KafkaProducerOneFactory")
    public ProducerFactory<String, Object> producerOneFactory() {
        return new DefaultKafkaProducerFactory<String, Object>(producerConfigs(1));
    }

    @Bean(name="KafkaProducerZero")
    public KafkaTemplate<String, Object> kafkaProducerZero() {
        return new KafkaTemplate<String, Object>(producerZeroFactory());
    }

    @Bean(name="KafkaProducerOne")
    public KafkaTemplate<String, Object> kafkaProducerOne() {
        return new KafkaTemplate<String, Object>(producerOneFactory());
    }

}
