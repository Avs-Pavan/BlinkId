package com.blink.blinkid.model

data class ErrorResponse(
    val description: String,
    val message: String,
    val statusCode: Int,
    val timestamp: String
)

data class LoginRequest(val email: String, val password: String)

data class LoginResponse(
    val email: String,
    val token: String,
    val user: User
)

data class User(
    val email: String,
    val id: Int,
    val images: List<Image>,
    val password: String,
    val roles: List<Role>,
    val status: String,
    val username: String
)

data class Role(
    val id: Int,
    val name: String
)

data class Image(
    val id: Int,
    val url: String
)

data class Exam(
    val admins: List<User> = emptyList(),
    val description: String,
    val examDate: String,
    val examDuration: String,
    val examLocation: String,
    val examTime: String,
    val id: Int? = null,
    val name: String,
    val users: List<User> = emptyList()
)