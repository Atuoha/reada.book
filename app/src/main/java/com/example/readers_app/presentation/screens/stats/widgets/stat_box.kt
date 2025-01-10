package com.example.readers_app.presentation.screens.stats.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun StatBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(135.dp)
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
    ) {

        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "My reading statistics", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Here's the stats of everything you have going on", style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 13.sp, fontFamily = FontFamily.Serif
                )
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Gray)) {
                    append("You've read: ")
                }
                withStyle(style = SpanStyle(color = Color.LightGray)) {
                    append("120 Books")
                }
            })
            Text(
                text = buildAnnotatedString {

                    withStyle(style = SpanStyle(color = Color.Gray)) {
                        append("You are reading: ")
                    }
                    withStyle(style = SpanStyle(color = Color.LightGray)) {
                        append("2 Books")
                    }
                }
            )
        }
    }
}