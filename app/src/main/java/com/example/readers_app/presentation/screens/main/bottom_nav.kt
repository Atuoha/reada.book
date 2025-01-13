package com.example.readers_app.presentation.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FloatingActionButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.readers_app.core.enums.Screens
import com.example.readers_app.domain.models.BottomNavItem
import com.example.readers_app.presentation.screens.book_marked.BookMarkedScreen
import com.example.readers_app.presentation.screens.home.HomeScreen
import com.example.readers_app.presentation.screens.main.widgets.BottomNaviItem
import com.example.readers_app.presentation.screens.profile.ProfileScreen
import com.example.readers_app.presentation.screens.stats.StatsScreen
import com.example.readers_app.ui.theme.primary

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNav(navigationController: NavController) {
    val navController = rememberNavController()

    return Scaffold(
        floatingActionButton = {
         if (navController.currentBackStackEntryAsState().value?.destination?.route == "home") FloatingActionButton(onClick = {
             navigationController.navigate(Screens.AddBook.name)

         },backgroundColor = primary,shape = RoundedCornerShape(10.dp)) {
              Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.White)
          }
        },


        bottomBar = { BottomBar(navController) }) {
        NavHost(
            navController = navController,
            startDestination = "home",
        ) {
            composable("home") { HomeScreen(navigationController) }
            composable("bookmarked") { BookMarkedScreen(navigationController) }
            composable("stats") { StatsScreen(navigationController) }
            composable("profile") { ProfileScreen(navigationController) }
        }

    }
}

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, "home"),
        BottomNavItem("Books", Icons.Default.Bookmark, "bookmarked"),
        BottomNavItem("Stats", Icons.AutoMirrored.Filled.ShowChart, "stats"),
        BottomNavItem("Profile", Icons.Default.Person, "profile")
    )

    BottomNavigation(
        backgroundColor = Color.White,
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            BottomNaviItem(item, currentRoute, navController)
        }
    }
}



