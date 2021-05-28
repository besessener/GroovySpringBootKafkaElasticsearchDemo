package me.spring.GroovyDemo.store

import me.spring.GroovyDemo.model.User
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ElasticsearchUserRepository extends ElasticsearchRepository<User, String> {
    User findByLastName(String lastName)
}
