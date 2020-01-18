package com.nibado.project.skeleton.users

class UserAlreadyExistsException(val userName: String) : RuntimeException("User $userName already exists")