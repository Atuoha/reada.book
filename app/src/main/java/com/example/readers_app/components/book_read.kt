package com.example.readers_app.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readers_app.R
import com.example.readers_app.domain.models.Book
import com.example.readers_app.ui.theme.primary


@Composable
fun BookRead(index: Int, book: Book) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
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
                    painter = painterResource(book.image),
                    contentDescription = "Book",
                    modifier = Modifier.clip(shape = RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = book.title,
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
                        text = book.author,
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
                                tint = primary,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                        }

                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "5.0",
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
                        tint = primary,
                        modifier = Modifier.clickable { }
                    )

                    Spacer(modifier = Modifier.height(15.dp))
                    Text( if(index % 2 == 0 ) "Started January 6th" else "Finished Dec 10th",style = TextStyle(color = Color.LightGray, fontFamily = FontFamily.Serif, fontSize = 13.sp))
                }
            }

        }
    }
}