package me.spring.GroovyDemo.model

import me.spring.GroovyDemo.stream.KafkaSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Users {
    @Autowired KafkaSender kafkaSender

    List<User> users = []

    void addUser(user) {
        kafkaSender.sendUserToStream(user)
    }

    List<User> getUsers(String sortBy, String order) {
        def sortedUsers = users.sort{it."$sortBy"}
        if (order.toLowerCase() == 'dsc') {
            sortedUsers = sortedUsers.reverse()
        }

        sortedUsers
    }

    User getUserByLastName(String lastName) {
        users.find { it.getLastName() == lastName }
    }
}