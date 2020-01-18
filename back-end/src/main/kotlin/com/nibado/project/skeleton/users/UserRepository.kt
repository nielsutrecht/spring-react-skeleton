package com.nibado.project.skeleton.users

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
class UserRepository {
    private val users = mutableMapOf<String, UserDetails>()

    fun findByUsername(name: String): UserDetails? = users[name]
    fun addUser(userDetails: UserDetails) {
        users[userDetails.username] = userDetails
    }
}
