package com.nibado.project.skeleton.users

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import java.security.SecureRandom
import java.util.*

@Repository
class UserTokenRepository {
    private val tokens = mutableMapOf<String, UserDetails>()

    fun findByToken(name: String): UserDetails? = tokens[name]

    fun deleteByUsername(name: String) {
        val keys = tokens.filter { it.value.username == name }.map { it.key }
        keys.forEach { tokens.remove(it) }
    }

    fun addUser(userDetails: UserDetails) : String {
        val token = nextToken()
        tokens[token] = userDetails

        return token
    }

    companion object {
        private val random = SecureRandom()

        fun nextToken() : String {
            val bytes = ByteArray(16)
            random.nextBytes(bytes)

            return Base64.getEncoder().encodeToString(bytes)
        }
    }
}
