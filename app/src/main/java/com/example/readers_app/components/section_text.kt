package com.example.readers_app.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SectionText(title: String, text: String) {
    Text(
        text = title,
        style = TextStyle(
            color = Color.Black,
            fontSize = 20.sp, fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Serif,
        ),
    )

    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = text,
        style = TextStyle(
            color = Color.Black,
            fontSize = 16.sp, fontWeight = FontWeight.ExtraLight,
            fontFamily = FontFamily.Serif,
        ),
    )
}