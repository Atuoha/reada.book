package com.example.readers_app.domain.models

data class Currently_Reading(val id: String?, val title: String?, val thumbnail: String?, val start_date: Long?, val end_date: Long?, val isReading: Boolean?) {
    constructor() : this("", "", "", 0, 0, false)


    fun toJson(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "title" to title,
            "thumbnail" to thumbnail,
            "start_date" to start_date,
            "end_date" to end_date,
            "isReading" to isReading
        )
    }
}



