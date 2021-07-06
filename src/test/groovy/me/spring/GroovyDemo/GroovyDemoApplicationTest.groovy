package me.spring.GroovyDemo

import me.spring.GroovyDemo.handler.UsersHandler
import me.spring.GroovyDemo.model.User
import me.spring.GroovyDemo.store.ElasticsearchUserRepository
import me.spring.GroovyDemo.stream.KafkaSender
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import spock.lang.Specification

@SpringBootTest(classes = GroovyDemoApplication.class)
class GroovyDemoApplicationTest extends Specification {

    @SpringBean
    ElasticsearchUserRepository elasticsearchUserRepository = Mock()

    @SpringBean
    KafkaSender kafkaSender = Mock()

    @SpringBean
    private KafkaTemplate<String, User> kafkaTemplate = Mock()

    @Autowired(required = false)
    UsersHandler users

    def "when context is loaded then all expected beans are created"() {
        expect:
            users
    }
}
