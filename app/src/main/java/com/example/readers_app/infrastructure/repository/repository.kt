package com.example.readers_app.infrastructure.repository

import com.example.readers_app.domain.data.DataOrException
import com.example.readers_app.domain.models.book_data.Item
import com.example.readers_app.infrastructure.network.BookApi
import javax.inject.Inject

class BookRepository @Inject constructor(private val bookApi: BookApi) {

    private val booksListDataOrException = DataOrException<List<Item>, Boolean, Exception>()
    private val bookDataOrException = DataOrException<Item, Boolean, Exception>()


    suspend fun getBooks(query: String): DataOrException<List<Item>, Boolean, Exception> {
        booksListDataOrException.data = null
        booksListDataOrException.error = null
        booksListDataOrException.loading = true

        try {
            val data = bookApi.getBooks(query).items
            if (data.isNotEmpty()) {
                booksListDataOrException.data = data
            } else {
                booksListDataOrException.error = Exception("No books found")
            }
        } catch (e: Exception) {
            booksListDataOrException.error = e

        } finally {
            booksListDataOrException.loading = false
        }

        return booksListDataOrException
    }


    suspend fun getBooksById(bookId: String): DataOrException<Item, Boolean, Exception> {
        bookDataOrException.data = null
        bookDataOrException.error = null
        bookDataOrException.loading = true

        try {
            val data = bookApi.getBookById(bookId)
            if (data != null) {
                bookDataOrException.data = data
            } else {
                bookDataOrException.error = Exception("Book not found")
            }
        } catch (e: Exception) {
            bookDataOrException.error = e
        } finally {
            bookDataOrException.loading = false
        }

        return bookDataOrException
    }
}