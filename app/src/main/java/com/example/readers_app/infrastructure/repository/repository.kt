package com.example.readers_app.infrastructure.repository

import com.example.readers_app.domain.data.DataOrException
import com.example.readers_app.domain.models.BookMarkedBook
import com.example.readers_app.domain.models.book_data.Item
import com.example.readers_app.infrastructure.network.BookApi
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class BookRepository @Inject constructor(private val bookApi: BookApi) {

    private val booksListDataOrException = DataOrException<List<Item>, Boolean, Exception>()
    private val bookDataOrException = DataOrException<Item, Boolean, Exception>()
    private val bookMarkedBooksListDataOrException =
        DataOrException<List<BookMarkedBook>, Boolean, Exception>()

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


    suspend fun getBookMarkedBooks(): DataOrException<List<BookMarkedBook>, Boolean, Exception> {
        bookMarkedBooksListDataOrException.data = null
        bookMarkedBooksListDataOrException.error = null
        bookMarkedBooksListDataOrException.loading = true

        try {
            val bookMarkedBooks = suspendCoroutine { continuation ->
                Firebase.firestore.collection("book_marked").get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        val books = it.result.documents.map { document ->
                            document.toObject(BookMarkedBook::class.java)!!
                        }
                        continuation.resume(books)
                    } else {
                        continuation.resumeWithException(Exception("No BookMarked Books Found"))
                    }
                }
            }
            if (bookMarkedBooks.isNotEmpty()) {
                bookMarkedBooksListDataOrException.data = bookMarkedBooks
            } else {
                bookMarkedBooksListDataOrException.error = Exception("No BookMarked Books Found")
            }
        } catch (e: Exception) {
            bookMarkedBooksListDataOrException.error = e
        } finally {
            bookMarkedBooksListDataOrException.loading = false
        }
        return bookMarkedBooksListDataOrException
    }

    suspend fun getBooksById(bookId: String): DataOrException<Item, Boolean, Exception> {
        bookDataOrException.data = null
        bookDataOrException.error = null
        bookDataOrException.loading = true

        try {
            val data = bookApi.getBookById(bookId)
            if (data.toString().isNotEmpty()) {
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