package com.example.readers_app.infrastructure.repository

import com.example.readers_app.domain.data.DataOrException
import com.example.readers_app.domain.models.book_data.Item
import com.example.readers_app.infrastructure.network.BookApi
import javax.inject.Inject

class BookRepository @Inject constructor(private val bookApi: BookApi) {

    private val dataOrException = DataOrException<List<Item>, Boolean, Exception>()

    suspend fun getBooks(query: String): DataOrException<List<Item>, Boolean, Exception> {
        dataOrException.data = null
        dataOrException.error = null
        dataOrException.loading = true

        try {
           val data = bookApi.getBooks(query).items
            if (data.isNotEmpty()) {
                dataOrException.data = data
            } else {
                dataOrException.error = Exception("No books found")
            }
        } catch (e: Exception) {
            dataOrException.error = e

        } finally {
            dataOrException.loading = false
        }

        return dataOrException
    }
}