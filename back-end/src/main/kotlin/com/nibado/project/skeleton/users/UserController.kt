package com.nibado.project.skeleton.users

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {
    @PostMapping("/login")
    fun login(@RequestBody userLoginRequest: UserLoginRequest) : UserTokenResponse {
        val (userName, password) = userLoginRequest
        val token = userService.login(userName, password)

        return UserTokenResponse(token)
    }

    @PostMapping("/register")
    fun register(@RequestBody userRegistrationRequest: UserRegistrationRequest) : UserTokenResponse {
        val (userName, password) = userRegistrationRequest
        val token = userService.register(userName, password)

        return UserTokenResponse(token)
    }

    @DeleteMapping("/login")
    fun logout(principal: Principal?): ResponseEntity<Unit> {
        principal?.run { userService.logout(name) }

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/me")
    fun me(principal: Principal) : UserResponse {
        val user = userService.getUser(principal.name)
        return UserResponse(user.username, user.authorities.map { it.authority }.toSet())
    }

    data class UserResponse(val username: String, val authorities: Set<String>)
    data class UserLoginRequest(val username: String, val password: String)
    data class UserRegistrationRequest(val username: String, val password: String)
    data class UserTokenResponse(val token: String)
}
