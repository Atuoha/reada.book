package com.example.readers_app.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.readers_app.core.enums.Screens
import com.example.readers_app.core.utils.getGreeting
import com.example.readers_app.infrastructure.view_model.UserViewModel

@Composable
fun TopSection(navController: NavController) {
    val userViewModel = hiltViewModel<UserViewModel>()
    val user = userViewModel.user.value

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (user != null) {
                AsyncImage(
                    model = user.avatar, contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))
            Column {
                if (user != null) {
                    Text(
                        text = "Hi, ${user.username}",
                        style = MaterialTheme.typography.titleMedium
                    )
                } else {
                    Text(
                        text = "Loading...",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Text(
                    text = getGreeting(), style = TextStyle(
                        color = Color.LightGray,
                        fontSize = 13.sp, fontFamily = FontFamily.Serif
                    )
                )
            }

        }

        Box(
            modifier = Modifier
                .size(35.dp)
                .background(
                    color = Color.White,
                    shape = CircleShape
                ), contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.Default.Search,
                contentDescription = "Search",
                modifier = Modifier.clickable { navController.navigate(Screens.AddBook.name) })
        }

    }
}