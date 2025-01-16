package com.example.readers_app.infrastructure.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readers_app.domain.data.DataOrException
import com.example.readers_app.domain.models.book_data.Item
import com.example.readers_app.infrastructure.repository.BookRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookViewModel @Inject constructor(private val bookRepository: BookRepository) : ViewModel() {
    private val _books = MutableLiveData<DataOrException<List<Item>, Boolean, Exception>>()
    val books: MutableLiveData<DataOrException<List<Item>, Boolean, Exception>> = _books


    suspend fun getBooks(query: String){
        viewModelScope.launch {
            _books.value = DataOrException(loading = true)
            try{
                val data = bookRepository.getBooks(query)
                if (data.data?.isNotEmpty() == true){
                    _books.value = DataOrException(error = Exception("No books found"))
                    Log.d("NO BOOKS", "getBooks:NO BOOKS FOUND" )
                }else{
                    _books.value = data
                    Log.d("BOOKS FOUND", "getBooks: ${_books.value}" )
                }
            }catch (e: Exception){
                _books.value = DataOrException(error = e)
                Log.d("BOOKS ERROR", "getBooks: ${e.message}" )
            }finally {
                _books.value = DataOrException(loading = false)
            }


        }
    }


}