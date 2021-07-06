package me.spring.GroovyDemo.stream

import me.spring.GroovyDemo.AppConstants
import me.spring.GroovyDemo.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback

@Component
class KafkaSender {

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate

    void sendUserToStream(User user) {
        ListenableFuture<SendResult<String, User>> future = kafkaTemplate.send(AppConstants.KAFKA_TOPIC_USER, user)
        future.addCallback(new ListenableFutureCallback<SendResult<String, User>>() {
            @Override
            void onSuccess(SendResult<String, User> result) {
                println("Sent message=[" + user + "] with offset=[" + result.getRecordMetadata().offset() + "]")
            }

            @Override
            void onFailure(Throwable ex) {
                println("Unable to send message=[" + user + "] due to : " + ex.getMessage())
            }
        })
    }
}
