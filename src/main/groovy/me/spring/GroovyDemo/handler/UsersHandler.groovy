package me.spring.GroovyDemo.handler

import me.spring.GroovyDemo.model.User
import me.spring.GroovyDemo.store.ElasticsearchUserRepository
import me.spring.GroovyDemo.stream.KafkaSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class UsersHandler {
    @Autowired
    KafkaSender kafkaSender

    @Autowired
    ElasticsearchUserRepository elasticsearchUserRepository

    void callApiAddUser(user) {
        kafkaSender.sendUserToStream(user)
    }

    void storeUserInElastic(User user) {
        elasticsearchUserRepository.save(user)
    }

    List<User> callApiGetAllUsers(String sortBy, String order) {
        Sort sort = Sort.by(Sort.Direction.valueOf(order.toUpperCase()), sortBy)
        elasticsearchUserRepository.findAll(sort).toList()
    }

    User callApiGetUserByLastName(String lastName) {
        elasticsearchUserRepository.findByLastName(lastName)
    }
}