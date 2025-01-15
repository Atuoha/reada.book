package com.example.readers_app.domain.models

data class ReadaUser
    (
    val userId: String?,
    val username: String?,
    val email: String?,
    val avatar: String?,
) {

    constructor() : this(null, null, null, null)

    fun toMap(): Map<String, String?> {
        return mapOf(
            "userId" to userId,
            "username" to username,
            "email" to email,
            "avatar" to avatar,
        )
    }

    fun fromMap(map: Map<String, Any>): ReadaUser {
        return ReadaUser(
            userId = map["userId"] as String,
            username = map["username"] as String,
            email = map["email"] as String,
            avatar = map["avatarUrl"] as String,
        )
    }
}