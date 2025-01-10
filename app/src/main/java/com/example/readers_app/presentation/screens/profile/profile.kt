package com.example.readers_app.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readers_app.R
import com.example.readers_app.components.ProfileLink

@Composable
fun ProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp, horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(10.dp))
        Text(text = "Profile", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Everything you need, all in one section.", style = TextStyle(
                color = Color.LightGray,
                fontSize = 13.sp, fontFamily = FontFamily.Serif
            )
        )

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile", modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = "Fatima Mohammed", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "fatima@gmail.com", style = TextStyle(
                        color = Color.LightGray,
                        fontSize = 13.sp, fontFamily = FontFamily.Serif
                    )
                )
            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        ProfileLink("Edit Profile") {

        }
        Spacer(modifier = Modifier.height(15.dp))
        ProfileLink("Change Password") {

        }
        Spacer(modifier = Modifier.height(15.dp))
        ProfileLink("Privacy Policy") {

        }
        Spacer(modifier = Modifier.height(15.dp))
        ProfileLink("Delete Account") {

        }
        Spacer(modifier = Modifier.height(15.dp))
        ProfileLink("Logout", hasDivider = false) {

        }
    }
}

