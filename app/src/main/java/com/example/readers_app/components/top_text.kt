package com.example.readers_app.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopText(title:String, subTitle:String) {
    Text(text = title, style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = subTitle,
        style = MaterialTheme.typography.bodyMedium
    )
}