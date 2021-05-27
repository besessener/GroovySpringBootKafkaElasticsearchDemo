package me.spring.GroovyDemo.handler

import me.spring.GroovyDemo.model.User
import me.spring.GroovyDemo.stream.KafkaSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UsersHandler {
    @Autowired
    KafkaSender kafkaSender

    List<User> users = []

    void callApiAddUser(user) {
        kafkaSender.sendUserToStream(user)
    }

    List<User> callApiGetAllUsers(String sortBy, String order) {
        def sortedUsers = users.sort { it."$sortBy" }
        if (order.toLowerCase() == 'dsc') {
            sortedUsers = sortedUsers.reverse()
        }

        sortedUsers
    }

    User callApiGetUserByLastName(String lastName) {
        users.find { it.getLastName() == lastName }
    }

    void storeUserInElastic(User user) {
        println user.toString()
    }
}