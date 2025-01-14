package com.example.readers_app.domain.models

data class ReadaUser
    (
    val userId: String,
    val username: String,
    val email: String,
    val avatarUrl: String,
) {

        fun toMap(): Map<String, Any> {
            return mapOf(
                "userId" to userId,
                "username" to username,
                "email" to email,
                "avatarUrl" to avatarUrl,
            )
        }

        fun fromMap(map: Map<String, Any>): ReadaUser {
            return ReadaUser(
                userId = map["userId"] as String,
                username = map["username"] as String,
                email = map["email"] as String,
                avatarUrl = map["avatarUrl"] as String,
            )
        }
}