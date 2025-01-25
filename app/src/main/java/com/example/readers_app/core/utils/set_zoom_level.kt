package com.example.readers_app.core.utils

fun String.setZoomLevel(zoomLevel: Int): String {
    val regex = "(zoom=)\\d+".toRegex()
    return if (regex.containsMatchIn(this)) {
        this.replace(regex, "zoom=$zoomLevel")
    } else {
        "$this&zoom=$zoomLevel"
    }
}