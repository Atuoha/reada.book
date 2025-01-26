package com.example.readers_app.domain.models

import java.util.Date

data class CurrentlyReading(
    val id: String?,
    val title: String?,
    val author: String?,
    val thumbnail: String?,
    val start_date: Date? = null,
    val end_date: Date? = null,
    val isReading: Boolean?
) {
    constructor() : this(
        "", "", "", "", start_date = Date(),
        end_date = Date(System.currentTimeMillis() + 86400000), false
    )


    fun toJson(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "title" to title,
            "author" to author,
            "thumbnail" to thumbnail,
            "start_date" to start_date,
            "end_date" to end_date,
            "isReading" to isReading
        )
    }
}



