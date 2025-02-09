package com.example.readers_app.presentation.screens.stats.widgets

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
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.readers_app.R
import com.example.readers_app.core.app_strings.AppStrings
import com.example.readers_app.core.utils.setZoomLevel
import com.example.readers_app.core.utils.toHttps
import com.example.readers_app.domain.models.BookMarkedBook
import com.example.readers_app.domain.models.CurrentlyReading
import com.example.readers_app.ui.theme.primary
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun BookRead(book: CurrentlyReading, onClick: () -> Unit) {
    val context = LocalContext.current
    val bookMarked = remember { mutableStateOf(false) }
    val starCount = remember { mutableIntStateOf(0) }
    val dateFormat = SimpleDateFormat("EEE, MMMM dd, yyyy", Locale.getDefault())

    fun bookMark() {
        bookMarked.value = !bookMarked.value

        try {
            if (bookMarked.value) {
                val bookMarkBook = BookMarkedBook(
                    id = book.id!!,
                    title = book.title,
                    thumbnail = book.thumbnail,
                    authors = book.author,
                    thoughts = "",
                    rating = starCount.intValue.toDouble(),
                )


                Firebase.firestore.collection("book_marked").document(book.id).set(
                    bookMarkBook.toJson()
                ).addOnSuccessListener {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(context, "Bookmarked", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Firebase.firestore.collection("book_marked").document(book.id!!).delete()
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
        Firebase.firestore.collection("book_marked").document(book.id!!).get()
            .addOnSuccessListener {
                bookMarked.value = it.exists()
                if(it.get("rating") != null)
                starCount.intValue = it.get("rating").toString().toDouble().toInt()
                else
                    starCount.intValue = 0
            }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .clickable {
                onClick()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Image(
                    painter = rememberImagePainter(
                        data = book.thumbnail?.toHttps()?.setZoomLevel(10)
                            ?: AppStrings.BOOK_IMAGE_PLACEHOLDER,
                        builder = {
                            crossfade(true)
                            error(R.drawable.error_img_big)
                            placeholder(R.drawable.placeholder)
                        }
                    ),
                    contentDescription = "Book Cover",
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(5.dp))
                        .width(80.dp),
                )


                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = book.title ?: "",
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
                        text = book.author ?: "",
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
                                tint = if (i <= starCount.intValue) primary else Color.LightGray,
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
                        tint = if (bookMarked.value) primary else Color.LightGray,
                        modifier = Modifier.clickable {
                            bookMark()
                        }
                    )

                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        "Started ${dateFormat.format(book.start_date)}",

                        style = TextStyle(
                            color = Color.LightGray,
                            fontFamily = FontFamily.Serif,
                            fontSize = 13.sp
                        )
                    )
                    Text(
                        if (book.isReading)
                            "Started on ${dateFormat.format(book.start_date)}"
                        else
                            "Finished ${
                                if (book.end_date == null) "Not yet" else "on " + dateFormat.format(
                                    book.end_date
                                )
                            }",
                        style = TextStyle(
                            color = Color.LightGray,
                            fontFamily = FontFamily.Serif,
                            fontSize = 13.sp
                        )
                    )
                }
            }

        }
    }
}