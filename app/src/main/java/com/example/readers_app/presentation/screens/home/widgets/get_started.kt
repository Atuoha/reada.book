package com.example.readers_app.presentation.screens.home.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readers_app.R
import com.example.readers_app.ui.theme.primary


@Composable
fun GetStarted(onGetStartedClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
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
                Column {
                    Text(
                        text = "Read Smarter, Dream\nBigger",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            onGetStartedClicked()
                        }, colors = ButtonDefaults.buttonColors(containerColor = primary),
                        border = BorderStroke(1.dp, primary),
                        modifier = Modifier
                            .height(30.dp)
                            .width(100.dp),
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        Text(
                            text = "Get started",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 14.sp, fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily.Serif,
                            ),
                        )

                    }
                }
                Image(
                    painter = painterResource(R.drawable.read2),
                    contentDescription = "Read",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}