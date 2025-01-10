package com.example.readers_app.presentation.screens.entry

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readers_app.R
import com.example.readers_app.components.CustomBTN
import com.example.readers_app.components.CustomBTNWhiteBG
import com.example.readers_app.components.SectionText
import com.example.readers_app.core.enums.Screens

@Composable
fun EntryScreen(navController: NavController) {
    return Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 25.dp)
    ) {
        Spacer(modifier = Modifier.height(160.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Reada.book", style = TextStyle(
                color = Color.Black,
                fontSize = 30.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif,

                )
        )
        Image(
            modifier = Modifier
                .size(300.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "Logo"
        )
        Spacer(modifier = Modifier.height(60.dp))
        SectionText("Hi, welcome to Reada!", "Please login or register to continue")
        Spacer(modifier = Modifier.height(50.dp))
        CustomBTNWhiteBG("Login") {
            navController.navigate(Screens.Login.name)
        }
        Spacer(modifier = Modifier.height(10.dp))
        CustomBTN("Register") {
            navController.navigate(Screens.Register.name)
        }

    }
}



