package com.example.readers_app.presentation.screens.home

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
import com.example.readers_app.components.SectionWithAll
import com.example.readers_app.components.SingleBook
import com.example.readers_app.core.enums.Screens
import com.example.readers_app.infrastructure.view_model.BookViewModel
import com.example.readers_app.presentation.screens.home.widgets.CarouselComponent
import com.example.readers_app.presentation.screens.home.widgets.CategorySection
import com.example.readers_app.presentation.screens.home.widgets.CurrentlyReading
import com.example.readers_app.presentation.screens.home.widgets.GetStarted
import com.example.readers_app.widgets.TopSection


@Composable
fun HomeScreen(navController: NavController, bookViewModel: BookViewModel = hiltViewModel()) {
    val currentCategoryIndex = remember { mutableIntStateOf(0) }
    val categories = listOf("Fiction", "Non Fiction", "Health", "Technology", "History", "Science")
    val error = remember { mutableStateOf(false) }
    val isLoading = remember {
        mutableStateOf(true)
    }
    LaunchedEffect(currentCategoryIndex.intValue) {
        isLoading.value = true
        error.value = false
        bookViewModel.getBooks(categories[currentCategoryIndex.intValue])
    }

    LaunchedEffect(bookViewModel.books.value) {
        when {
            bookViewModel.books.value.loading == true -> {
                Log.d("Loading", "BOOK IS LOADING...")
                isLoading.value = true
            }

            bookViewModel.books.value.error != null -> {
                Log.d("Error", "BOOK HAS ERROR")

                isLoading.value = false
                error.value = true
            }

            bookViewModel.books.value.data != null -> {
                Log.d("Data", "BOOK IS LOADING...")

                isLoading.value = false
                error.value = false
            }
        }
    }

    fun categorySelection(index: Int) {
        isLoading.value = true
        currentCategoryIndex.intValue = index
        Log.d("Category", "Category Selected: $index")
    }

    Column(
        modifier = Modifier
            .padding(vertical = 20.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            TopSection(navController)
            Spacer(modifier = Modifier.height(10.dp))
            GetStarted()
            Spacer(modifier = Modifier.height(10.dp))
            SectionWithAll(title = "Currently Reading") {
                navController.navigate(Screens.CurrentlyReading.name)
            }
            Spacer(modifier = Modifier.height(5.dp))
            CurrentlyReading()
            Spacer(modifier = Modifier.height(10.dp))
        }
        CarouselComponent()
        Spacer(modifier = Modifier.height(10.dp))
        CategorySection(categories, currentCategoryIndex) {
            categorySelection(it)
        }
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
                            style = androidx.compose.material3.MaterialTheme.typography.titleMedium
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
                            style = androidx.compose.material3.MaterialTheme.typography.titleMedium
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
                    bookViewModel.books.value.data?.let { books ->
                        items(books.size) { index ->
                            val book = books[index]
                            Log.d("FROM BOOK", "Book Title: ${book.volumeInfo.title}")
                            SingleBook(book = book) {
                                book.id.let { id ->
                                    navController.navigate("${Screens.Details.name}/$id")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}








