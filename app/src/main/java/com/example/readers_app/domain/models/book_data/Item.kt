package com.example.readers_app.domain.models.book_data

data class Item(
    val accessInfo: AccessInfo,
    val etag: String,
    val id: String,
    val kind: String,
    val saleInfo: SaleInfo,
    val searchInfo: SearchInfo,
    val selfLink: String,
    val volumeInfo: VolumeInfo
)