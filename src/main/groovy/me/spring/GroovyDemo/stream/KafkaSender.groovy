package me.spring.GroovyDemo.stream

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
    private KafkaTemplate<String, String> kafkaTemplate;

    void sendUserToStream(User user) {
        ListenableFuture<SendResult<String, User>> future = kafkaTemplate.send('my-sample-topic', user)
        future.addCallback(new ListenableFutureCallback<SendResult<String, User>>() {
            @Override
            void onSuccess(SendResult<String, User> result) {
                System.out.println("Sent message=[" + user + "] with offset=[" + result.getRecordMetadata().offset() + "]")
            }

            @Override
            void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[" + user + "] due to : " + ex.getMessage())
            }
        });
    }
}
