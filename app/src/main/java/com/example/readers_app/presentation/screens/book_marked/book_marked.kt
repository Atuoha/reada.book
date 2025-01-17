package com.example.readers_app.presentation.screens.book_marked

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.readers_app.components.SingleBook
import com.example.readers_app.core.enums.Screens
import com.example.readers_app.infrastructure.view_model.BookViewModel

@SuppressLint("DiscouragedApi")
@Composable
fun BookMarkedScreen(navController: NavController) {
    val context = LocalContext.current

    val bookViewModel = hiltViewModel<BookViewModel>()

    LaunchedEffect(Unit) {
        bookViewModel.getBooks("Fiction")
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            bookViewModel.books.value.data?.count()?.let {
                items(it) { index ->
                    val book =  bookViewModel.books.value.data!![index]
                    SingleBook(book = book){
                        navController.navigate("${Screens.Details.name}/${book.id}")
                    }
                }
            }
        }
    }
}