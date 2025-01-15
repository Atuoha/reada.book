package com.example.readers_app.presentation.screens.add_book

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readers_app.domain.models.books
import com.example.readers_app.presentation.screens.add_book.widgets.BookReadAdd
import com.example.readers_app.presentation.screens.update_book.widgets.TextInputField

@Composable
fun AddBookScreen(navController: NavController) {
    val search = remember {
        mutableStateOf("")
    }

    fun search(){

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
                })
        }) { innerPadding ->


        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.padding(
                    top = 10.dp,
                    start = 18.dp,
                    end = 18.dp
                )
            ) {
                TextInputField(
                    valueState = search,
                    isSingleLine = true,
                    labelId = "Enter book title",
                    enabled = true,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                    icon = Icons.Default.Search,
                    placeholder = "Search book"
                )
                Spacer(modifier = Modifier.height(15.dp))
                LazyColumn {
                    items(books.count()) { index ->
                        val book = books[index]

                        BookReadAdd(index,book)
                    }
                }
            }
        }
    }
}