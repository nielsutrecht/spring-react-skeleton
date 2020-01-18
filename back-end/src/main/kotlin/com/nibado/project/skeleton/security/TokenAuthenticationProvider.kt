package com.nibado.project.skeleton.security

import com.nibado.project.skeleton.users.UserRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class TokenAuthenticationProvider(private val userRepository: UserRepository) : AbstractUserDetailsAuthenticationProvider() {
    override fun retrieveUser(userName: String, authentication: UsernamePasswordAuthenticationToken): UserDetails {
        val token = authentication.credentials as String

        return userRepository.loadUserByToken(token) ?: throw UsernameNotFoundException("token")
    }

    override fun additionalAuthenticationChecks(user: UserDetails, authentication: UsernamePasswordAuthenticationToken) {
    }
}