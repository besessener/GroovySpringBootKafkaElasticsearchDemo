package me.spring.GroovyDemo.stream


import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaListeners {

    @KafkaListener(topics = "my-sample-topic", groupId = "UserGroup")
    void listenGroupFoo(String message) {
        println("Received Message in group UserGroup: " + message)
    }
}
