package me.spring.GroovyDemo.stream

import me.spring.GroovyDemo.AppConstants
import me.spring.GroovyDemo.handler.UsersHandler
import me.spring.GroovyDemo.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaListeners {

    @Autowired UsersHandler users

    @KafkaListener(topics = AppConstants.KAFKA_TOPIC_USER, groupId = AppConstants.KAFKA_GROUP_USER)
    void listenGroupUser(User user) {
        users.storeUserInElastic(user)
    }
}
