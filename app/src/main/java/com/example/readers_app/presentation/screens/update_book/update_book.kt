package com.example.readers_app.presentation.screens.update_book

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readers_app.components.CustomBTN
import com.example.readers_app.core.app_strings.AppStrings
import com.example.readers_app.domain.models.Book
import com.example.readers_app.domain.models.books
import com.example.readers_app.presentation.screens.details.widgets.BookCoverImage
import com.example.readers_app.presentation.screens.update_book.widgets.TextInputField
import com.example.readers_app.ui.theme.primary

@Composable
fun UpdateBookScreen(navController: NavController, id: String = "3"){
    val book: Book = books.find { it.id == id } ?: return
    val starCount = remember { mutableIntStateOf(0) }
    val review = remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
           FloatingActionButton(onClick = {

            },backgroundColor = primary,shape = RoundedCornerShape(10.dp)) {
                androidx.compose.material.Icon(imageVector = Icons.Default.Save, contentDescription = "Add", tint = Color.White)
            }
        },
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
                        "Book Section",
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

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {

                BookCoverImage(AppStrings.BOOK_IMAGE_PLACEHOLDER)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = book.author,
                    style = TextStyle(
                        color = Color.LightGray,
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row {
                        for (j in 1..5) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Star",
                                tint = if (j <= starCount.intValue) primary else Color.LightGray,
                                modifier = Modifier.size(15.dp).clickable {

                                    if (j < 1) {
                                        starCount.intValue = 0
                                    } else {
                                        starCount.intValue = j
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(2.dp))

                        }
                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = starCount.intValue.toString(),
                            style = TextStyle(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Light,
                                fontSize = 15.sp,
                                color = Color.LightGray,
                            ),
                            modifier = Modifier.padding(start = 1.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                    }
                    Icon(imageVector = Icons.Default.Bookmark,
                        contentDescription = "",
                        tint = primary, modifier = Modifier
                            .size(22.dp)
                            .clickable { })
                }
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(thickness = 0.3.dp)
                Spacer(modifier = Modifier.height(10.dp))

                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Gray, fontSize = 13.sp,
                            fontFamily = FontFamily.Serif
                        )
                    ) {
                        append("Number of Pages: ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.LightGray, fontSize = 12.sp,
                            fontFamily = FontFamily.Serif
                        )
                    ) {
                        append("${book.pageCount} pages")
                    }
                })


                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Gray, fontSize = 12.sp,
                            fontFamily = FontFamily.Serif
                        )
                    ) {
                        append("Book Preview Link: ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.LightGray, fontSize = 13.sp,
                            fontFamily = FontFamily.Serif
                        )
                    ) {
                        append(book.previewLink)
                    }
                })
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    "Description", style = TextStyle(
                        color = Color.Black, fontSize = 12.sp,
                        fontFamily = FontFamily.Serif, fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(book.description, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(15.dp))
                    TextInputField(
                        isSingleLine = false,
                        valueState = review,
                        labelId = "Your Review",
                        enabled = true,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                        icon = Icons.Default.RateReview,
                        onAction = KeyboardActions.Default,
                        placeholder = "Your thoughts"
                    )
                Spacer(modifier = Modifier.height(15.dp))
               Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                   Box(modifier = Modifier.fillMaxWidth(0.40f)){

                       CustomBTN("Start Reading") {
                       }
                   }
                   Spacer(modifier = Modifier.width(10.dp))

                   Box(modifier = Modifier.fillMaxWidth(0.70f)){
                       CustomBTN("Stop Reading", containerColor = Color.LightGray) {
                       }
                   }
               }
                }
                Spacer(modifier = Modifier.height(20.dp))
        }

    }
}