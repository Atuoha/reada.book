package com.example.readers_app.infrastructure.network

import com.example.readers_app.domain.models.book_data.BookData
import com.example.readers_app.domain.models.book_data.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BookApi {

    @GET("volumes")
    suspend fun getBooks(@Query("q") query: String): BookData

    @GET("volumes/{bookId}")
    suspend fun getBookById(@Path("bookId") bookId: String): Item
}