package me.spring.GroovyDemo.model

import groovy.transform.Canonical
import groovy.transform.TupleConstructor
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

import java.time.Instant

@Canonical
@Document(indexName = "user")
class User {
    @Id
    @Schema(hidden = true)
    String id = "${Instant.now().toString()}_${UUID.randomUUID().toString()}"

    @Schema(hidden = true)
    @Field(type = FieldType.Date, name = "insertDate")
    Date insertDate = Date.from(Instant.now())

    @Field(type = FieldType.Text, name = "firstName")
    String firstName

    @Field(type = FieldType.Text, name = "lastName")
    String lastName

    @Field(type = FieldType.Integer, name = "age")
    Integer age
}
