package com.example.readers_app.infrastructure.repository

import android.util.Log
import com.example.readers_app.domain.data.DataOrException
import com.example.readers_app.domain.models.BookMarkedBook
import com.example.readers_app.domain.models.CurrentlyReading
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

    private val currentlyReadingBooksListDataOrException =
        DataOrException<List<CurrentlyReading>, Boolean, Exception>()

    private val currentlyReadingBookDataOrException =
        DataOrException<CurrentlyReading, Boolean, Exception>()

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
                    if (it.isSuccessful && it.result.documents.isNotEmpty()) {
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

    suspend fun getCurrentlyReadingBooks(): DataOrException<List<CurrentlyReading>, Boolean, Exception> {
        currentlyReadingBooksListDataOrException.data = null
        currentlyReadingBooksListDataOrException.error = null
        currentlyReadingBooksListDataOrException.loading = true

        try {
            val currentlyReadingBooks = suspendCoroutine { continuation ->
                Firebase.firestore.collection("currently_reading").get().addOnCompleteListener {
                    if (it.isSuccessful && it.result.documents.isNotEmpty()) {
                        val books = it.result.documents.map { document ->
                            document.toObject(CurrentlyReading::class.java)!!
                        }
                        continuation.resume(books)
                    } else {
                        continuation.resumeWithException(Exception("No Currently Reading Books Found"))
                    }
                }
            }
            if (currentlyReadingBooks.isNotEmpty()) {
                currentlyReadingBooksListDataOrException.data = currentlyReadingBooks
            } else {
                currentlyReadingBooksListDataOrException.error =
                    Exception("No Currently Reading Books Found")
            }
        } catch (e: Exception) {
            currentlyReadingBooksListDataOrException.error = e
        } finally {
            currentlyReadingBooksListDataOrException.loading = false
        }
        return currentlyReadingBooksListDataOrException
    }


    suspend fun getCurrentlyReadingBook(): DataOrException<CurrentlyReading, Boolean, Exception> {
        currentlyReadingBookDataOrException.data = null
        currentlyReadingBookDataOrException.error = null
        currentlyReadingBookDataOrException.loading = true

        try {
            val currentlyReadingBook = suspendCoroutine { continuation ->
                Firebase.firestore.collection("currently_reading").whereEqualTo("isReading", true)
                    .get()
                    .addOnCompleteListener {
                        if (it.isSuccessful && it.result.documents.isNotEmpty()) {
                            val books = it.result.documents.map { document ->
                                document.toObject(CurrentlyReading::class.java)!!
                            }
                            continuation.resume(books.last())
                        } else {
                            continuation.resumeWithException(Exception("No Currently Reading Book Found"))
                        }
                    }
            }
            if (currentlyReadingBook != null) {
                currentlyReadingBookDataOrException.data = currentlyReadingBook
            } else {
                currentlyReadingBookDataOrException.error =
                    Exception("No Currently Reading Book Found")
            }
        } catch (e: Exception) {
            currentlyReadingBookDataOrException.error = Exception("No Currently Reading Book Found")
        } finally {
            currentlyReadingBookDataOrException.loading = false
        }
        return currentlyReadingBookDataOrException
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