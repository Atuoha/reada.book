package com.example.readers_app.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readers_app.domain.models.Book
import com.example.readers_app.ui.theme.primary

@Composable
fun SingleBook(book: Book) {
    Box(
        modifier = Modifier
            .padding(start = 7.dp, top = 7.dp)
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(165.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
        ) {
            Image(
                painter = painterResource(book.image),
                contentDescription = "Book",
                modifier = Modifier.clip(shape = RoundedCornerShape(5.dp)),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = book.title,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                    ),
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = book.author,
                    style = TextStyle(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Light,
                        fontSize = 11.sp,
                        color = Color.LightGray,
                    ),
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    for (j in 1..5) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star",
                            tint = primary,
                            modifier = Modifier.size(10.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                }
                Text(
                    text = "5.0",
                    style = TextStyle(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Light,
                        fontSize = 10.sp,
                        color = Color.LightGray,
                    ),
                    modifier = Modifier.padding(start = 1.dp)
                )
                Icon(imageVector = Icons.Default.Bookmark,
                    contentDescription = "",
                    tint = primary, modifier = Modifier
                        .size(10.dp)
                        .clickable { })
            }
        }
    }
}