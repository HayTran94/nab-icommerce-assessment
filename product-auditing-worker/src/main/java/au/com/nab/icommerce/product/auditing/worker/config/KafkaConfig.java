package au.com.nab.icommerce.product.auditing.worker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<String, String> cdcProductConsumerFactory() {
        Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
        return new DefaultKafkaConsumerFactory<>(consumerProperties);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> cdcProductKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cdcProductConsumerFactory());
        factory.setConcurrency(3);
        return factory;
    }

}
