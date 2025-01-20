package com.example.readers_app.infrastructure.view_model

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readers_app.domain.data.DataOrException
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

    fun getBooks(query: String) {
        viewModelScope.launch {
            _books.value = DataOrException(loading = true)
            try {
                val data = bookRepository.getBooks(query)
                if (data.data?.isNotEmpty() == true) {
                    _books.value = DataOrException(data = data.data, loading = false)
                    Log.d(
                        "BOOKS FOUND",
                        "getBooks: ${_books.value.data?.first()?.volumeInfo?.title}"
                    )

                    Log.d("BOOK SIZE", "${books.value.data?.count()}")
                    books.value.data?.forEach {
                        Log.d("BOOK", "Book Title: ${it.volumeInfo.title}")
                        Log.d("BOOK IMAGE", "Book Image: ${it.volumeInfo.imageLinks?.thumbnail}")
                    }
                } else {
                    _books.value =
                        DataOrException(error = Exception("No books found"), loading = false)
                    Log.d("NO BOOKS", "getBooks:NO BOOKS FOUND")
                }
            } catch (e: Exception) {
                _books.value = DataOrException(error = e)
                Log.d("BOOKS ERROR", "getBooks: ${e.message}")
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