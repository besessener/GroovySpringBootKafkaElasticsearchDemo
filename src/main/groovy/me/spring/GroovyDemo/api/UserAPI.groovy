package me.spring.GroovyDemo.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

import me.spring.GroovyDemo.model.User
import me.spring.GroovyDemo.handler.UsersHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "User", description = "the User API")
@RequestMapping("/users")
class UserAPI {

    @Autowired
    UsersHandler users

    @Operation(summary = "Return list of all users")
    @ApiResponses([@ApiResponse(responseCode = "200", description = "Users successfully retrieved", content = [
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))])])
    @GetMapping("/all")
    List<User> allUsers(
            @Parameter(description = "Define by which value you want to sort.") @RequestParam(required = false, defaultValue = 'insertDate') String sortBy,
            @Parameter(description = "Define whether to sort ascending ('asc') or descending ('desc')") @RequestParam(required = false, defaultValue = 'asc') String order) {
        users.callApiGetAllUsers(sortBy, order)
    }

    @Operation(summary = "Return a special user")
    @ApiResponses([@ApiResponse(responseCode = "200", description = "User successfully retrieved", content = [
            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))])])
    @GetMapping("/special")
    User specialUser() {
        users.callApiGetSpecialUser()
    }

    @Operation(summary = "Get specific user by last name")
    @ApiResponses([
            @ApiResponse(responseCode = "200", description = "User successfully retrieved", content = [
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))]),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    ])
    @GetMapping("/{lastName}")
    ResponseEntity<User> userByLastName(@PathVariable() String lastName) {
        def user = users.callApiGetUserByLastName(lastName)
        new ResponseEntity<>(user, user ? HttpStatus.OK : HttpStatus.NOT_FOUND)
    }

    @Operation(summary = "Add a new User")
    @ApiResponses([@ApiResponse(responseCode = "200", description = "User successfully added")])
    @PostMapping(value = "/add", consumes = "application/json")
    void addUser(
            @Parameter(description = "JSON representation of a User", required=true, schema=@Schema(implementation = User.class)) @RequestBody(required = true) User user) {
        users.callApiAddUser(user)
    }
}
