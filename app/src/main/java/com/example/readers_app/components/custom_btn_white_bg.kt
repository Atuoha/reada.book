package com.example.readers_app.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun CustomBTNWhiteBG(title:String,containerColor: Color = Color.White, onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
        }, colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        border = ButtonDefaults.outlinedButtonBorder, modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp, fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Serif,
            ),
        )

    }
}