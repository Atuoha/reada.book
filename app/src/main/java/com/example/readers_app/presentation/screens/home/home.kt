package com.example.readers_app.presentation.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController){

    Text(
        text = "Home Screen",
        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
    )
}