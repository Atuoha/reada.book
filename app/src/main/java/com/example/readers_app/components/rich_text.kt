package com.example.readers_app.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle

@Composable
fun RichTextNav(text1:String, text2:String, onClick: ()->Unit = {}) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        textAlign = TextAlign.Center,
        text = buildAnnotatedString {
            append(text1)
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(text2)
            }
        }
    )
}