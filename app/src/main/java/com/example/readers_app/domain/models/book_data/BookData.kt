package com.example.readers_app.domain.models.book_data

data class BookData(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)