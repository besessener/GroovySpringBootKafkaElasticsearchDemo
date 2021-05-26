package me.spring.GroovyDemo.stream

import me.spring.GroovyDemo.AppConstants
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaListeners {

    @KafkaListener(topics = AppConstants.KAFKA_TOPIC_USER, groupId = AppConstants.KAFKA_GROUP_USER)
    void listenGroupFoo(String message) {
        println("Received Message in group ${AppConstants.KAFKA_GROUP_USER}: " + message)
    }
}
