package com.example.readers_app.presentation.screens.details.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BookCoverImage(bookImg: Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.48f)
                .height(180.dp)
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                .align(alignment = Alignment.End)
        ) {
            Image(
                painter = painterResource(bookImg),
                contentDescription = "Book Img",
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(5.dp))
                    .padding(10.dp),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}