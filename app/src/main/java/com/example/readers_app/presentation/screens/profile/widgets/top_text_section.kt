package com.example.readers_app.presentation.screens.profile.widgets

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopTextSection() {
    Text(text = "Profile", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = "Everything you need, all in one section.", style = TextStyle(
            color = Color.LightGray,
            fontSize = 13.sp, fontFamily = FontFamily.Serif
        )
    )
}