package com.example.readers_app.infrastructure.network

import com.example.readers_app.domain.models.book_data.BookData
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BookApi {

    @GET("volumes/{q}")
    suspend fun getBooks(@Query("q") query: String): BookData
}