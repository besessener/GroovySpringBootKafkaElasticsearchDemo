package me.spring.GroovyDemo.handler

import me.spring.GroovyDemo.model.User
import org.springframework.stereotype.Component

@Component
class UsersHandle {
    List<User> users = []

    void addUser(String firstName, String lastName, Integer age) {
        users.add(new User(firstName, lastName, age))
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