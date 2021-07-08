package me.spring.GroovyDemo.stream

import me.spring.GroovyDemo.handler.UsersHandler
import me.spring.GroovyDemo.model.User
import me.spring.GroovyDemo.store.ElasticsearchUserRepository
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import spock.util.concurrent.AsyncConditions

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"])
class KafkaSendAndReceiveTest extends Specification {

    @SpringBean
    ElasticsearchUserRepository elasticsearchUserRepository = Mock()

    @Autowired
    KafkaSender kafkaSender

    @SpringBean
    private UsersHandler users = Mock()

    def "when sending a user it shall be put to stream"() {
        given:
            def user = new User("123", new Date(0))
        and:
            def buffer = new ByteArrayOutputStream()
            System.out = new PrintStream(buffer)

        when:
            kafkaSender.sendUserToStream(user)
            sleep(5 * 1000)

        then:
            buffer.toString().contains("Sent message=[me.spring.GroovyDemo.model.User(123, Thu Jan 01 01:00:00 CET 1970, null, null, null)] with offset=[0]")
            buffer.toString().contains("Received message=[me.spring.GroovyDemo.model.User(123, Thu Jan 01 01:00:00 CET 1970, null, null, null)]")
    }

}