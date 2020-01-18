package com.nibado.project.skeleton.security

import com.nibado.project.skeleton.users.UserService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class TokenAuthenticationProvider(private val userService: UserService) : AbstractUserDetailsAuthenticationProvider() {
    override fun retrieveUser(userName: String, authentication: UsernamePasswordAuthenticationToken): UserDetails {
        val token = authentication.credentials as String

        return userService.findByToken(token) ?: throw UsernameNotFoundException("token")
    }

    override fun additionalAuthenticationChecks(user: UserDetails, authentication: UsernamePasswordAuthenticationToken) {
    }
}