package com.example.readers_app.presentation.screens.add_book.widgets

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.readers_app.R
import com.example.readers_app.core.app_strings.AppStrings
import com.example.readers_app.core.utils.setZoomLevel
import com.example.readers_app.core.utils.toHttps
import com.example.readers_app.domain.models.book_data.Item
import com.example.readers_app.ui.theme.primary


@Composable
fun BookReadAdd(index: Int, book: Item, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            //.height(180.dp)
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
                        data = book.volumeInfo.imageLinks?.thumbnail?.toHttps()?.setZoomLevel(10)
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
                        .width(80.dp)
                        .height(80.dp),
                )

                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = book.volumeInfo?.title ?: "",
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
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
                        text = book.volumeInfo?.authors?.get(0) ?: "Unknown",
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
                                tint = if(i <= book.volumeInfo.averageRating.toInt()) primary else Color.LightGray,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                        }

                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "${book.volumeInfo.averageRating.toInt()}.0",
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
                    Row() {
                        Text(
                            text = ("Publish Date: " + book.volumeInfo.publishedDate) ?: "Unknown",
                            style = TextStyle(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Light,
                                fontSize = 11.sp,
                                lineHeight = 25.sp,
                                letterSpacing = 0.sp,
                                color = Color.Black,
                            ),
                        )

                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = ("Category: " + book.volumeInfo?.categories?.get(0))
                                ?: "Unknown",
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Light,
                                fontSize = 11.sp,
                                lineHeight = 25.sp,
                                letterSpacing = 0.sp,
                                color = Color.Black,
                            ),
                        )
                    }



                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            ("Language " + book.volumeInfo?.language) ?: "Unknown",
                            style = TextStyle(
                                color = Color.LightGray,
                                fontFamily = FontFamily.Serif, fontSize = 13.sp
                            )
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Gray, fontSize = 13.sp,
                                    fontFamily = FontFamily.Serif
                                )
                            ) {
                                append("Pages: ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = Color.LightGray, fontSize = 12.sp,
                                    fontFamily = FontFamily.Serif
                                )
                            ) {
                                append("${book?.volumeInfo?.pageCount} pages")
                            }
                        })
                    }



                }
            }

        }
    }
}