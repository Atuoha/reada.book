package com.example.readers_app.presentation.screens.book_marked

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.readers_app.R
import com.example.readers_app.core.enums.Screens
import com.example.readers_app.infrastructure.view_model.BookViewModel
import com.example.readers_app.presentation.screens.book_marked.widgets.SingleBookMark

@SuppressLint("DiscouragedApi")
@Composable
fun BookMarkedScreen(navController: NavController) {
    val error = remember { mutableStateOf(false) }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val bookViewModel = hiltViewModel<BookViewModel>()


    LaunchedEffect(Unit) {
        isLoading.value = true
        error.value = false
        bookViewModel.getBookMarkedBooks()
    }

    LaunchedEffect(bookViewModel.bookMarkedBooks.value) {
        when {
            bookViewModel.bookMarkedBooks.value.loading == true -> {
                Log.d("Loading", "BOOK IS LOADING...")
                isLoading.value = true
            }

            bookViewModel.bookMarkedBooks.value.error != null -> {
                Log.d("Error", "BOOK HAS ERROR")
                isLoading.value = false
                error.value = true
            }

            bookViewModel.bookMarkedBooks.value.data != null -> {
                Log.d("Data", "BOOK IS LOADING...")
                isLoading.value = false
                error.value = false
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(vertical = 20.dp)) {
        Column(modifier = Modifier.padding(horizontal = 16.dp) ) {
            Spacer(Modifier.height(10.dp))
            Text(text = "All your bookmarked books", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Here you can find all your bookmarked books", style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 13.sp, fontFamily = FontFamily.Serif
                )
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

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
                            text = "${bookViewModel.books.value.error?.message}",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(5.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(0.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    bookViewModel.bookMarkedBooks.value.data?.count()?.let {
                        items(it) { index ->
                            val book =  bookViewModel.bookMarkedBooks.value.data!![index]
                            SingleBookMark(book = book){
                                navController.navigate("${Screens.Details.name}/${book.id}")
                            }
                        }
                    }
                }
            }
        }



    }
}