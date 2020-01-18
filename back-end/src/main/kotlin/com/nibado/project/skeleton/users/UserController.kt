package com.nibado.project.skeleton.users

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/user")
class UserController {
    @GetMapping("/login")
    fun login(principal: Principal?) : UserLoggedInResponse {
        return UserLoggedInResponse(principal != null)
    }

    @PostMapping("/login")
    fun login(@RequestBody userLoginRequest: UserLoginRequest) : UserLoginResponse {
        return UserLoginResponse("abcdef")
    }

    @DeleteMapping("/login")
    fun logout(): ResponseEntity<Unit> {
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/me")
    fun me(principal: Principal) : UserResponse {
        return UserResponse(principal.name)
    }

    data class UserLoggedInResponse(val boolean: Boolean)
    data class UserResponse(val username: String)
    data class UserLoginRequest(val username: String, val password: String)
    data class UserLoginResponse(val token: String)
}
