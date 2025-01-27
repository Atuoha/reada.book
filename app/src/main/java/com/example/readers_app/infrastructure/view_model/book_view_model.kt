package com.example.readers_app.infrastructure.view_model

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readers_app.domain.data.DataOrException
import com.example.readers_app.domain.models.BookMarkedBook
import com.example.readers_app.domain.models.CurrentlyReading
import com.example.readers_app.domain.models.book_data.Item
import com.example.readers_app.infrastructure.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val bookRepository: BookRepository) : ViewModel() {
    private val _books: MutableState<DataOrException<List<Item>, Boolean, Exception>> =
        mutableStateOf(
            DataOrException()
        )
    val books: State<DataOrException<List<Item>, Boolean, Exception>> = _books

    // single book
    private val _book: MutableState<DataOrException<Item, Boolean, Exception>> =
        mutableStateOf(
            DataOrException()
        )
    val book: State<DataOrException<Item, Boolean, Exception>> = _book


    private val _bookmarkedBooks: MutableState<DataOrException<List<BookMarkedBook>, Boolean, Exception>> =
        mutableStateOf(
            DataOrException()
        )
    val bookMarkedBooks: State<DataOrException<List<BookMarkedBook>, Boolean, Exception>> = _bookmarkedBooks

    private val _currentlyReadingBooks: MutableState<DataOrException<List<CurrentlyReading>, Boolean, Exception>> =
        mutableStateOf(
            DataOrException()
        )

    val currentlyReadingBooks: State<DataOrException<List<CurrentlyReading>, Boolean, Exception>> = _currentlyReadingBooks

    private val _currentlyReadingBook: MutableState<DataOrException<CurrentlyReading, Boolean, Exception>> =
        mutableStateOf(
            DataOrException()
        )
    val currentlyReadingBook: State<DataOrException<CurrentlyReading, Boolean, Exception>> = _currentlyReadingBook


    fun getBooks(query: String) {
        viewModelScope.launch {
            _books.value = DataOrException(loading = true)
            try {
                val data = bookRepository.getBooks(query)
                if (data.data?.isNotEmpty() == true) {
                    _books.value = DataOrException(data = data.data, loading = false)
                } else {
                    _books.value =
                        DataOrException(error = Exception("No books found"), loading = false)
                }
            } catch (e: Exception) {
                _books.value = DataOrException(error = e)
                Log.d("BOOKS ERROR", "getBooks: ${e.message}")
            }
        }
    }


    fun getBookMarkedBooks() {
        viewModelScope.launch {
            _bookmarkedBooks.value = DataOrException(loading = true)
            try {
                val data = bookRepository.getBookMarkedBooks()
                if (data.data?.isNotEmpty() == true) {
                    _bookmarkedBooks.value = DataOrException(data = data.data, loading = false)

                    bookMarkedBooks.value.data?.forEach {
                        Log.d("BOOK", "Book Title: ${it.title}")
                        Log.d("BOOK IMAGE", "Book Image: ${it.thumbnail}")
                    }
                } else {
                    _bookmarkedBooks.value =
                        DataOrException(error = Exception("No books found"), loading = false)
                    Log.d("NO BOOKS", "bookMarkedBooks: NO BOOKS FOUND")
                }
            } catch (e: Exception) {
                _bookmarkedBooks.value = DataOrException(error = e)
                Log.d("BOOKS ERROR", "bookMarkedBooks: ${e.message}")
            }
        }
    }

    fun getCurrentlyReadingBooks() {
        viewModelScope.launch {
            _currentlyReadingBooks.value = DataOrException(loading = true)
            try {
                val data = bookRepository.getCurrentlyReadingBooks()
                if (data.data?.isNotEmpty() == true) {
                    _currentlyReadingBooks.value = DataOrException(data = data.data, loading = false)
                    currentlyReadingBooks.value.data?.forEach {
                        Log.d("BOOK", "Book Title: ${it.title}")
                        Log.d("Reading", "${it.isReading}")
                    }
                } else {
                    _currentlyReadingBooks.value =
                        DataOrException(error = Exception("No books found"), loading = false)
                    Log.d("NO BOOKS", "currentlyReadingBooks: NO BOOKS FOUND")
                }
            } catch (e: Exception) {
                _currentlyReadingBooks.value = DataOrException(error = e)
                Log.d("BOOKS ERROR", "currentlyReadingBooks: ${e.message}")
            }
        }
    }

    fun getCurrentlyReadingBook(){
        viewModelScope.launch {
            _currentlyReadingBook.value = DataOrException(loading = true)

            try {
                val data = bookRepository.getCurrentlyReadingBook()
                if (data.data != null) {
                    _currentlyReadingBook.value = DataOrException(data = data.data, loading = false)
                } else {
                    _currentlyReadingBook.value =
                        DataOrException(error = Exception("No Currently Reading Book Found"), loading = false)
                }
            } catch (e: Exception) {
                _currentlyReadingBook.value = DataOrException(error = e)
            }
        }
    }

    fun getBookById(bookId: String){
        viewModelScope.launch {
            _book.value = DataOrException(loading = true)

            try {
                val data = bookRepository.getBooksById(bookId)
                if (data.data != null) {
                    _book.value = DataOrException(data = data.data, loading = false)
                    Log.d(
                        "BOOK FOUND",
                        "getBookById: ${_book.value.data?.volumeInfo?.title}"
                    )
                } else {
                    _book.value =
                        DataOrException(error = Exception("No book found"), loading = false)
                    Log.d("NO BOOK", "getBookById:NO BOOK FOUND")
                }
            } catch (e: Exception) {
                _book.value = DataOrException(error = e)
                Log.d("BOOK ERROR", "getBookById: ${e.message}")
            }
        }
    }



}