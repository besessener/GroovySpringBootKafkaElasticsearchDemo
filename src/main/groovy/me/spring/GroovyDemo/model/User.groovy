package me.spring.GroovyDemo.model

import groovy.transform.Canonical
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Canonical
@Document(indexName = "User")
class User {
    @Id
    String lastName

    @Field(type = FieldType.Text, name = "firstName")
    String firstName

    @Field(type = FieldType.Integer, name = "age")
    Integer age
}
