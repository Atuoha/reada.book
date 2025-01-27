package com.example.readers_app.presentation.screens.home.widgets

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.readers_app.R
import com.example.readers_app.core.app_strings.AppStrings
import com.example.readers_app.core.utils.setZoomLevel
import com.example.readers_app.core.utils.toHttps
import com.example.readers_app.domain.models.BookMarkedBook
import com.example.readers_app.domain.models.CurrentlyReading
import com.example.readers_app.infrastructure.view_model.BookViewModel
import com.example.readers_app.ui.theme.primary
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


@Composable
fun CurrentlyReading(bookViewModel: BookViewModel = hiltViewModel(), onClick: (String) -> Unit) {

    val context = LocalContext.current
    val error = remember { mutableStateOf(false) }
    val isLoading = remember {
        mutableStateOf(true)
    }
    val book = remember { mutableStateOf<CurrentlyReading?>(null) }
    val bookMarked = remember { mutableStateOf(false) }
    val starCount = remember { mutableIntStateOf(0) }

    fun bookMark() {
        bookMarked.value = !bookMarked.value

        try {
            if (bookMarked.value) {
                val bookMarkBook = BookMarkedBook(
                    id = book.value?.id!!,
                    title = book.value?.title!!,
                    thumbnail = book.value?.thumbnail!!,
                    authors = book.value?.author!!,
                    thoughts = "",
                    rating = starCount.intValue.toDouble(),
                )


                Firebase.firestore.collection("book_marked").document(book.value?.id!!).set(
                    bookMarkBook.toJson()
                ).addOnSuccessListener {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(context, "Bookmarked", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Firebase.firestore.collection("book_marked").document(book.value?.id!!).delete()
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
        bookViewModel.getCurrentlyReadingBook()
    }

    LaunchedEffect(bookViewModel.currentlyReadingBook.value) {
        when {
            bookViewModel.currentlyReadingBook.value.loading == true -> {
                isLoading.value = true
            }

            bookViewModel.currentlyReadingBook.value.error != null -> {
                isLoading.value = false
                error.value = true
            }

            bookViewModel.currentlyReadingBook.value.data != null -> {
                isLoading.value = false
                book.value = bookViewModel.currentlyReadingBook.value.data
                Firebase.firestore.collection("book_marked").document(book.value?.id!!).get().addOnSuccessListener {
                    bookMarked.value = it.exists()
                    starCount.intValue = it.get("rating").toString().toDouble().toInt()?:  0
                }
                error.value = false
            }
        }
    }



    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
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
                            contentDescription = "Connection Error",
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "${bookViewModel.currentlyReadingBook.value.error?.message}",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp).clickable {
                            book.value?.id?.let { onClick(it) }
                        },
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = book.value?.thumbnail?.toHttps()?.setZoomLevel(10)  ?: AppStrings.BOOK_IMAGE_PLACEHOLDER,
                                builder = {
                                    crossfade(true)
                                    error(R.drawable.error_img_big)
                                    placeholder(R.drawable.placeholder)
                                }
                            ),
                            contentDescription = "Book Cover",
                            modifier = Modifier.clip(shape = RoundedCornerShape(5.dp)).width(80.dp),
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = book.value?.title ?: "",
                                style = TextStyle(
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    lineHeight = 25.sp,
                                    letterSpacing = 0.sp
                                ),
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = book.value?.author ?:"",
                                style = TextStyle(
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Light,
                                    fontSize = 11.sp,
                                    lineHeight = 25.sp,
                                    letterSpacing = 0.sp,
                                    color = Color.LightGray,
                                ),
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                for (i in 1..5) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Star",
                                        tint = if(i <= starCount.intValue) primary else Color.LightGray,
                                        modifier = Modifier.size(15.dp)
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                }

                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "${starCount.intValue}.0",
                                    style = TextStyle(
                                        fontFamily = FontFamily.Serif,
                                        fontWeight = FontWeight.Light,
                                        fontSize = 11.sp,
                                        lineHeight = 25.sp,
                                        letterSpacing = 0.sp,
                                        color = Color.LightGray,
                                    ),
                                )
                            }

                            Spacer(modifier = Modifier.height(5.dp))
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = "",
                                tint = if(bookMarked.value) primary else Color.LightGray,
                                modifier = Modifier.clickable {
                                    bookMark()
                                }
                            )
                        }
                    }

                }




            }
        }


    }
}