package au.com.nab.icommerce.product.auditing.worker.config;

import au.com.nab.icommerce.product.auditing.worker.domain.CDCMessage;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<String, CDCMessage> cdcProductConsumerFactory() {
        Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
        return new DefaultKafkaConsumerFactory<>(
                consumerProperties,
                new StringDeserializer(),
                new JsonDeserializer<>(CDCMessage.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CDCMessage> cdcProductKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CDCMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cdcProductConsumerFactory());
        factory.setConcurrency(3);
        return factory;
    }

}
