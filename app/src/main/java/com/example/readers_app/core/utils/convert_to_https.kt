package com.example.readers_app.core.utils

fun String.toHttps(): String {
    return if (this.startsWith("http://")) {
        this.replaceFirst("http://", "https://")
    } else {
        this
    }
}
