package com.example.readers_app.presentation.screens.add_details

import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.readers_app.R
import com.example.readers_app.core.app_strings.AppStrings
import com.example.readers_app.core.enums.Screens
import com.example.readers_app.core.utils.cleanDescription
import com.example.readers_app.core.utils.setZoomLevel
import com.example.readers_app.core.utils.toHttps
import com.example.readers_app.domain.models.book_data.Item
import com.example.readers_app.infrastructure.view_model.BookViewModel
import com.example.readers_app.presentation.screens.details.widgets.BookCoverImage
import com.example.readers_app.ui.theme.primary
import com.example.readers_app.domain.models.BookMarkedBook
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


@Composable
fun AddDetailsScreen(
    navController: NavController,
    id: String,
    bookViewModel: BookViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val book = remember { mutableStateOf<Item?>(null) }
    val error = remember { mutableStateOf(false) }
    val isLoading = remember {
        mutableStateOf(true)
    }

    val bookMarked = remember { mutableStateOf(false) }

    fun bookMark() {
        bookMarked.value = !bookMarked.value

        try {
            if (bookMarked.value) {
                val bookMarkBook = BookMarkedBook(
                    id = id,
                    title = book.value?.volumeInfo?.title,
                    thumbnail = book.value?.volumeInfo?.imageLinks?.thumbnail,
                    authors = book.value?.volumeInfo?.authors?.get(0),
                    rating = book.value?.volumeInfo?.averageRating,
                    thoughts = "",
                )


                Firebase.firestore.collection("book_marked").document(id).set(
                    bookMarkBook.toJson()
                ).addOnSuccessListener {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(context, "Bookmarked", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Firebase.firestore.collection("book_marked").document(id).delete()
                    .addOnSuccessListener {
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(context, "Bookmark Removed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        } catch (e: Exception) {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
            Log.d("Error", e.message.toString())
        }

    }

    LaunchedEffect(Unit) {
        isLoading.value = true
        error.value = false
        bookViewModel.getBookById(id)

        Firebase.firestore.collection("book_marked").document(id).get().addOnSuccessListener {
            bookMarked.value = it.exists()
        }
    }

    LaunchedEffect(bookViewModel.book.value) {
        when {
            bookViewModel.book.value.loading == true -> {
                Log.d("Loading", "BOOK IS LOADING...")
                isLoading.value = true
            }

            bookViewModel.book.value.error != null -> {
                Log.d("Error", "BOOK HAS ERROR")

                isLoading.value = false
                error.value = true
            }

            bookViewModel.book.value.data != null -> {
                Log.d("Data", "BOOK IS LOADING...")
                book.value = bookViewModel.book.value.data
                isLoading.value = false
                error.value = false
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            if (!isLoading.value && bookMarked.value) FloatingActionButton(
                onClick = {
                    navController.navigate("${Screens.UpdateBook.name}/${id}")
                },
                backgroundColor = primary,
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color.White
                )
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
                        "Book Details",
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
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {

                when {
                    isLoading.value -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
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
                            contentAlignment = Alignment.Center
                        ) {
                            Column {
                                Image(
                                    painter = painterResource(id = R.drawable.opps),
                                    contentDescription = "Connection Error"
                                )
                                Text(
                                    text = "An error occurred: ${bookViewModel.books.value.error?.message}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }

                    else -> {


                        BookCoverImage(
                            book.value?.volumeInfo?.imageLinks?.thumbnail?.toHttps()
                                ?.setZoomLevel(6)
                                ?: AppStrings.BOOK_IMAGE_PLACEHOLDER
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = book.value?.volumeInfo?.title ?: "",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = book.value?.volumeInfo?.authors?.get(0) ?: "",
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
                                        tint = if (j <= book.value?.volumeInfo?.averageRating?.toInt()!!) primary else Color.LightGray,
                                        modifier = Modifier.size(15.dp)
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))

                                }
                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    text = "${book.value?.volumeInfo?.averageRating?.toInt()!!}.0",
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
                                tint = if (bookMarked.value) primary else Color.LightGray,
                                modifier = Modifier
                                    .size(22.dp)
                                    .clickable {
                                        bookMark()
                                    })
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
                                append("${book.value?.volumeInfo?.pageCount} pages")
                            }
                        })
                        Text(text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Gray, fontSize = 12.sp,
                                    fontFamily = FontFamily.Serif
                                )
                            ) {
                                append("Book Preview Link (click to view): ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = Color.LightGray, fontSize = 13.sp,
                                    fontFamily = FontFamily.Serif
                                )
                            ) {
                                append(book.value?.volumeInfo?.previewLink ?: "")
                            }
                        }, modifier = Modifier.clickable {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(book.value?.volumeInfo?.previewLink ?: "")
                            )
                            context.startActivity(intent)
                        })
                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            "Description", style = TextStyle(
                                color = Color.Black, fontSize = 12.sp,
                                fontFamily = FontFamily.Serif, fontWeight = FontWeight.SemiBold
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            cleanDescription(book.value?.volumeInfo?.description ?: ""),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

            }
        }
    }
}

