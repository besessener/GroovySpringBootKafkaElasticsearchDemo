package me.spring.GroovyDemo.api

import groovy.json.JsonBuilder
import me.spring.GroovyDemo.GroovyDemoApplication
import me.spring.GroovyDemo.handler.UsersHandler
import me.spring.GroovyDemo.model.User
import me.spring.GroovyDemo.store.ElasticsearchUserRepository
import me.spring.GroovyDemo.stream.KafkaSender
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(classes = GroovyDemoApplication.class)
class UserAPITest extends Specification {

    @SpringBean
    private UsersHandler users = Mock()

    @SpringBean
    ElasticsearchUserRepository elasticsearchUserRepository = Mock()

    @SpringBean
    KafkaSender kafkaSender = Mock()

    @SpringBean
    private KafkaTemplate<String, User> kafkaTemplate = Mock()

    @Autowired
    private MockMvc mvc

    def "/users/all : the response has status 200 and content is an empty list"() {
        when:
            def response = mvc.perform(get('/users/all').param('sortBy', 'insertDate').param('order', 'asc'))
                    .andExpect(status().isOk())
                    .andReturn()
                    .response
                    .contentAsString

        then:
            1 * users.callApiGetAllUsers('insertDate', 'asc') >> []
            '[]' == response
    }

    def "/users/special : the response has status 200 and content is a dummy User"() {
        when:
            def response = mvc.perform(get('/users/special'))
                    .andExpect(status().isOk())
                    .andReturn()
                    .response
                    .contentAsString

        then:
            1 * users.callApiGetSpecialUser() >> new User('id', new Date(0))
            '{"id":"id","insertDate":"1970-01-01T00:00:00.000+00:00","firstName":null,"lastName":null,"age":null}' == response
    }

    @Unroll
    def "/users/#name : the response has status #code"() {
        given:
            users.callApiGetUserByLastName(name) >> response

        expect:
            mvc.perform(get("/users/$name"))
                    .andExpect(status)

        where:
            name            | status                | response   | code
            "yourFunnyName" | status().isOk()       | new User() | "200"
            "unknownName"   | status().isNotFound() | null       | "404"
    }

    def "/users/add : the response has status 200 and something was added"() {
        given:
            def user = new User('id', new Date(0))

        when:
            def apiCall = mvc.perform(
                    post('/users/add')
                        .content(new JsonBuilder(user).toPrettyString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

        then:
            apiCall.andExpect(status().isOk())
            1 * users.callApiAddUser(user)
    }
}