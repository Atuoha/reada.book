package com.example.readers_app.domain.models

data class BookMarkedBook(
    val id: String, val title: String?,
    val thumbnail: String?, val authors: String?, val rating: Double?, val thoughts: String?
) {
    constructor() : this("", "", "", "", 0.0,"")
    fun toJson(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "title" to title,
            "thumbnail" to thumbnail,
            "authors" to authors,
            "rating" to rating,
            "thoughts" to thoughts
        )
    }
}
