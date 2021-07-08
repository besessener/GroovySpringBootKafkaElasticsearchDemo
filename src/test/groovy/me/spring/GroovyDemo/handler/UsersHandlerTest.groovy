package me.spring.GroovyDemo.handler

import me.spring.GroovyDemo.GroovyDemoApplication
import me.spring.GroovyDemo.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

@DirtiesContext
@SpringBootTest(classes = GroovyDemoApplication.class)
class UsersHandlerTest extends Specification {

    @Autowired
    UsersHandler usersHandler

    def user
    def userWithLastName

    def setup() {
        user = new User("123", new Date(0))
        userWithLastName = new User("124", new Date(1), 'coolFirstName', 'fancyLastName', 42)

        usersHandler.storeUserInElastic(user)
        usersHandler.storeUserInElastic(userWithLastName)
    }

    def "CallApiGetAllUsers"() {
        expect:
            usersHandler.callApiGetAllUsers('insertDate', 'asc') == [user, userWithLastName]
            usersHandler.callApiGetAllUsers('insertDate', 'desc') == [userWithLastName, user]
    }

    def "CallApiGetUserByLastName"() {
        expect:
            usersHandler.callApiGetUserByLastName(null) == user
            usersHandler.callApiGetUserByLastName('fancyLastName') == userWithLastName
    }

    def "CallApiGetSpecialUser"() {
        expect:
            usersHandler.callApiGetSpecialUser() == userWithLastName
    }
}
