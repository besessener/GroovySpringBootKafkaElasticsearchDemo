package me.spring.GroovyDemo.store

import me.spring.GroovyDemo.model.User
import org.springframework.data.elasticsearch.annotations.Query
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ElasticsearchUserRepository extends ElasticsearchRepository<User, String> {
    User findByLastName(String lastName)

    @Query("{\"bool\": {\"must\": [{\"match\": {\"age\": 42}}]}}")
    User findSpecial()
}
