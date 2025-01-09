package com.example.readers_app.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun SectionWithAll(title: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = title, style = MaterialTheme.typography.titleSmall)
        Text(
            text = "See all",
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Light,
                fontSize = 11.sp,
                lineHeight = 25.sp,
                letterSpacing = 0.sp,
                color = Color.LightGray,
            ),
            modifier = Modifier.clickable { onClick() }
        )
    }
}
