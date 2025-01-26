package com.example.readers_app.presentation.screens.add_book

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.readers_app.R
import com.example.readers_app.core.enums.Screens
import com.example.readers_app.infrastructure.view_model.BookViewModel
import com.example.readers_app.presentation.screens.add_book.widgets.BookReadAdd
import com.example.readers_app.presentation.screens.add_book.widgets.SearchInputField

@Composable
fun AddBookScreen(navController: NavController, bookViewModel: BookViewModel = hiltViewModel()) {
    val search = remember {
        mutableStateOf("")
    }
    val error = remember { mutableStateOf(false) }
    val isLoading = remember {
        mutableStateOf(false)
    }

    fun search() {
        if (search.value.isNotEmpty()) {
            isLoading.value = true
            error.value = false
            bookViewModel.getBooks(search.value)
            Log.d("SEARCH", "search: ${search.value}")
        }
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

    return Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = Color.Transparent,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text(
                        "Search Book",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily.Serif,
                            letterSpacing = 1.sp
                        )
                    )
                },
                actions = {
                    // View Added books Button
//                    IconButton(onClick = {
//                        navController.navigate(Screens.AddedBooks.name)
//                    }) {
//                        Text(
//                            text = "View Added Books",
//                            style = TextStyle(
//                                color = Color.Black,
//                                fontSize = 14.sp,
//                                fontWeight = FontWeight.Medium,
//                                fontFamily = FontFamily.Serif,
//                                letterSpacing = 1.sp
//                            )
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(18.dp))

                }
            )
        }) { innerPadding ->


        Box(
            modifier = Modifier.padding(
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                top = innerPadding.calculateTopPadding(),
                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                bottom = 0.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 10.dp,
                    start = 18.dp,
                    end = 18.dp
                )
            ) {
                SearchInputField(
                    valueState = search,
                    isSingleLine = true,
                    labelId = "Enter book title",
                    enabled = true,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                    icon = Icons.Default.Search,
                    placeholder = "Search book",
                    onAction = KeyboardActions(
                        onSearch = {
                            search()
                        }
                    ),
                ) {
                    search()
                }
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
                                    contentDescription = "Connection Error",
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Text(
                                    text = "${bookViewModel.books.value.error?.message}",
                                    textAlign = TextAlign.Center,
                                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

                    else -> {

                        LazyColumn {
                            bookViewModel.books.value.data?.let { books ->
                                items(books.size) { index ->
                                    val book = books[index]
                                    Log.d("FROM BOOK", "Book Title: ${book.volumeInfo.title}")
                                    BookReadAdd(book = book, index = index) {
                                        book.id.let { id ->
                                            navController.navigate("${Screens.AddDetails.name}/$id")
                                        }
                                    }
                                }
                            }
                        }

                    }
                }


            }
        }
    }
}