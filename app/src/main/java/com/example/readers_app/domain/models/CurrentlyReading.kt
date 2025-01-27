package com.example.readers_app.domain.models

import java.util.Date

data class CurrentlyReading(
    val id: String?,
    val title: String?,
    val author: String?,
    val thumbnail: String?,
    val start_date: Date,
    val end_date: Date?,
    val isReading: Boolean
) {
    constructor() : this(
        "", "", "", "", start_date = Date(),
        end_date = Date(), false
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


    fun fromJson(json: Map<String, Any?>): CurrentlyReading {
        return CurrentlyReading(
            id = json["id"] as String?,
            title = json["title"] as String?,
            author = json["author"] as String?,
            thumbnail = json["thumbnail"] as String?,
            start_date = json["start_date"] as Date,
            end_date = json["end_date"] as Date,
            isReading = json["isReading"] as Boolean
        )
    }
}



