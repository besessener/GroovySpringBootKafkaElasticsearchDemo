package me.spring.GroovyDemo.model

import groovy.transform.Canonical

@Canonical
class User {
    String firstName
    String lastName
    Integer age
}
