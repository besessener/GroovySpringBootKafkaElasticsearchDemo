package me.spring.GroovyDemo.model

import org.springframework.stereotype.Component

@Component
class Users {
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