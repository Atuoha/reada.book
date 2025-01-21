package com.example.readers_app.core.utils

fun cleanDescription(description: String): String {
    return description.replace(Regex("<[^>]*>"), "")
}

