package com.nibado.project.skeleton.users

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userTokenRepository: UserTokenRepository
) {

    fun login(userName: String, password: String): String {
        val user = userRepository.findByUsername(userName)
            ?: throw UsernameNotFoundException("User with name $userName not found")
        if (!ENCODER.matches(password, user.password)) {
            throw UsernameNotFoundException("Password incorrect for user $userName")
        }

        return userTokenRepository.addUser(user)
    }

    fun register(userName: String, password: String): String {
        if(userRepository.findByUsername(userName) != null) {
            throw UserAlreadyExistsException(userName)
        }

        val user = addUser(userName, password, "USER")

        return login(userName, password)
    }

    fun logout(userName: String) {
        userTokenRepository.deleteByUsername(userName)
    }

    fun findByToken(token: String): UserDetails {
        return userTokenRepository.findByToken(token)
            ?: throw UsernameNotFoundException("User with name not found for token")
    }

    fun getUser(userName: String): UserDetails =
        userRepository.findByUsername(userName) ?: throw IllegalStateException("Should not happen")

    private fun addUser(username: String, password: String, vararg roles: String) {
        val user = User.withUsername(username)
            .password(password)
            .roles(*roles)

            .passwordEncoder(ENCODER::encode)
            .build()

        userRepository.addUser(user)
    }

    @PostConstruct
    fun init() {
        addUser("john", "secret", "USER")
        addUser("jane", "supersecret", "USER", "ADMIN")
    }

    companion object {
        private val ENCODER = BCryptPasswordEncoder()
    }
}