package com.example.readers_app.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.readers_app.R
import com.example.readers_app.core.app_strings.AppStrings
import com.example.readers_app.core.utils.setZoomLevel
import com.example.readers_app.core.utils.toHttps
import com.example.readers_app.domain.models.BookMarkedBook
import com.example.readers_app.domain.models.book_data.Item
import com.example.readers_app.ui.theme.primary
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun SingleBook(book: Item, rating: Int, onClick: () -> Unit) {
    val context = LocalContext.current
    val bookMarked = remember { mutableStateOf(false) }

    fun bookMark() {
        bookMarked.value = !bookMarked.value

        try {
            if (bookMarked.value) {
                val bookMarkBook = BookMarkedBook(
                    id = book.id,
                    title = book.volumeInfo.title,
                    thumbnail = book.volumeInfo.imageLinks.thumbnail,
                    authors = book.volumeInfo.authors[0],
                    rating = book.volumeInfo.averageRating,
                    thoughts = "",
                )


                Firebase.firestore.collection("book_marked").document(book.id).set(
                    bookMarkBook.toJson()
                ).addOnSuccessListener {
                    Toast.makeText(context, "Bookmarked", Toast.LENGTH_SHORT).show()
                }
            } else {
                Firebase.firestore.collection("book_marked").document(book.id).delete()
                    .addOnSuccessListener {
                        Toast.makeText(context, "Bookmark Removed", Toast.LENGTH_SHORT).show()
                    }
            }

        } catch (e: Exception) {
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            Log.d("Error", e.message.toString())
        }

    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("book_marked").document(book.id).get().addOnSuccessListener {
            bookMarked.value = it.exists()
        }
    }



    Box(
        modifier = Modifier
            .padding(start = 7.dp, top = 7.dp)
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(165.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
        ) {

            Image(
                painter = rememberImagePainter(
                    data = book.volumeInfo.imageLinks.thumbnail.toHttps().setZoomLevel(10)
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
            Log.d(
                "BOOK IMAGE",
                book.volumeInfo.imageLinks.smallThumbnail ?: AppStrings.BOOK_IMAGE_PLACEHOLDER
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = book.volumeInfo.title ?: "",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                    ),
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = book.volumeInfo.authors[0] ?: "Unknown",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Light,
                        fontSize = 13.sp,
                        color = Color.LightGray,
                    ),
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    for (j in 1..5) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star",
                            tint = if (j <= rating) primary else Color.LightGray,
                            modifier = Modifier.size(10.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                }
                Text(
                    text = "$rating.0",
                    style = TextStyle(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Light,
                        fontSize = 13.sp,
                        color = Color.LightGray,
                    ),
                    modifier = Modifier.padding(start = 1.dp)
                )
                Icon(imageVector = Icons.Default.Bookmark,
                    contentDescription = "",
                    tint = if(bookMarked.value) primary else Color.LightGray, modifier = Modifier
                        .size(18.dp)
                        .clickable {
                            bookMark()
                        })
            }
        }
    }
}