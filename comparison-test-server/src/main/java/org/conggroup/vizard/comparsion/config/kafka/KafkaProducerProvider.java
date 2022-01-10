package org.conggroup.vizard.comparsion.config.kafka;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.kafka.KafkaConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class KafkaProducerProvider {

    private final Environment environment;

    public Map<String, Object> producerConfigs() {
        Integer serverNo = environment.getProperty("vizard.server.serverNo", Integer.class);
        String bootstrapServer = environment.getProperty(String.format("%s.%d", KafkaConstants.BOOTSTRAP_SERVER_PREFIX, serverNo));
        return KafkaProducerConfigTemplate.GenerateConfig(bootstrapServer);
    }

    @Bean(name="KafkaProducerFactory")
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<String, Object>(producerConfigs());
    }

    @Bean(name="KafkaProducer")
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<String, Object>(producerFactory());
    }

}
