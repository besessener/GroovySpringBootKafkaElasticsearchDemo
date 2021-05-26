package me.spring.GroovyDemo.stream

import me.spring.GroovyDemo.AppConstants
import me.spring.GroovyDemo.model.User
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@EnableKafka
@Configuration
class KafkaConsumerConfig {

    @Bean
    ConsumerFactory<String, User> consumerFactory() {
        Map<String, Object> props = new HashMap<>()
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                AppConstants.KAFKA_BOOTSTRAP_SERVER)
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                AppConstants.KAFKA_GROUP_USER)
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class)
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class)
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props)
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, User>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>()
        factory.setConsumerFactory(consumerFactory())
        return factory
    }
}