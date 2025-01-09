package com.example.readers_app.presentation.screens.main

import android.annotation.SuppressLint
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.readers_app.domain.models.BottomNavItem
import com.example.readers_app.presentation.screens.book_marked.BookMarkedScreen
import com.example.readers_app.presentation.screens.home.HomeScreen
import com.example.readers_app.presentation.screens.profile.ProfileScreen
import com.example.readers_app.presentation.screens.stats.StatsScreen
import com.example.readers_app.ui.theme.primary

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNav() {
    val navController = rememberNavController()

    return Scaffold(bottomBar = { BottomBar(navController) }) {
        NavHost(
            navController = navController,
            startDestination = "home",
//            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("bookmarked") { BookMarkedScreen(navController) }
            composable("stats") { StatsScreen(navController) }
            composable("profile") { ProfileScreen(navController) }
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
            BottomNavigationItem(

                selectedContentColor = primary,
                unselectedContentColor = Color.LightGray,
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}


