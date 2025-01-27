package com.example.readers_app.presentation.screens.stats

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.readers_app.R
import com.example.readers_app.core.enums.Screens
import com.example.readers_app.infrastructure.view_model.BookViewModel
import com.example.readers_app.presentation.screens.stats.widgets.BookRead
import com.example.readers_app.presentation.screens.stats.widgets.StatBox
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun StatsScreen(navController: NavController, bookViewModel: BookViewModel = hiltViewModel()) {

    val error = remember { mutableStateOf(false) }
    val isLoading = remember {
        mutableStateOf(true)
    }

    val booksRead = remember { mutableIntStateOf(0) }
    val currentlyReading = remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        isLoading.value = true
        error.value = false
        bookViewModel.getCurrentlyReadingBooks()

        Firebase.firestore.collection("currently_reading").whereEqualTo("isReading", true).get().addOnCompleteListener {
            if(it.isSuccessful){
               currentlyReading.intValue = it.result.size()
            }
        }

        Firebase.firestore.collection("books_read").whereEqualTo("isReading", false).get().addOnCompleteListener {
            if(it.isSuccessful){
               booksRead.intValue = it.result.size()
            }
        }
    }

    LaunchedEffect(bookViewModel.currentlyReadingBooks.value) {
        when {
            bookViewModel.currentlyReadingBooks.value.loading == true -> {
                Log.d("Loading", "BOOK IS LOADING...")
                isLoading.value = true
            }

            bookViewModel.currentlyReadingBooks.value.error != null -> {
                Log.d("Error", "BOOK HAS ERROR")

                isLoading.value = false
                error.value = true
            }

            bookViewModel.currentlyReadingBooks.value.data != null -> {
                Log.d("Data", "BOOK IS LOADING...")
                isLoading.value = false
                error.value = false
            }
        }
    }


   Column(modifier = Modifier
       .padding(horizontal = 16.dp, vertical = 20.dp)
       .fillMaxSize()) {
       Spacer(modifier = Modifier.height(10.dp))
       StatBox(booksRead.intValue, currentlyReading.intValue)
       Spacer(modifier = Modifier.height(15.dp))

       when {
           isLoading.value -> {
               Box(
                   modifier = Modifier.fillMaxSize(),
                   contentAlignment = androidx.compose.ui.Alignment.Center
               ) {
                   Column {
                       CircularProgressIndicator()
                       Spacer(modifier = Modifier.height(10.dp))
                       Text(
                           text = "Loading...",
                           style = MaterialTheme.typography.titleMedium
                       )
                   }
               }
           }
           error.value -> {
               Box(
                   modifier = Modifier.fillMaxSize(),
                   contentAlignment = androidx.compose.ui.Alignment.Center
               ) {
                   Column {
                       Image(
                           painter = painterResource(id = R.drawable.opps),
                           contentDescription = "Connection Error"
                       )
                       Text(
                           text = "${bookViewModel.currentlyReadingBooks.value.error?.message}",
                           style = MaterialTheme.typography.titleMedium
                       )
                   }
               }
           }
           else -> {
               LazyColumn {
                   bookViewModel.currentlyReadingBooks.value.data?.let {
                       items(it.count()) { index ->
                           val book =  bookViewModel.currentlyReadingBooks.value.data!![index]
                           BookRead(book){
                               navController.navigate("${Screens.Details.name}/${book.id}")
                           }
                       }
                   }
               }




           }
       }


   }
}
