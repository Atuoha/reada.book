package com.example.readers_app.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.readers_app.core.enums.Screens
import com.example.readers_app.domain.models.Book
import com.example.readers_app.presentation.screens.add_book.AddBookScreen
import com.example.readers_app.presentation.screens.details.DetailsScreen
import com.example.readers_app.presentation.screens.entry.EntryScreen
import com.example.readers_app.presentation.screens.forgot_password.ForgotPasswordScreen
import com.example.readers_app.presentation.screens.login.LoginScreen
import com.example.readers_app.presentation.screens.main.BottomNav
import com.example.readers_app.presentation.screens.register.RegisterScreen
import com.example.readers_app.presentation.screens.splash.SplashScreen
import com.example.readers_app.presentation.screens.update_book.UpdateBookScreen
import com.google.gson.Gson

@Composable
fun ReadaNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.Splash.name) {
        composable(route = Screens.Splash.name) {
            SplashScreen(navController = navController)
        }

        composable(route = Screens.Entry.name) {
            EntryScreen(navController = navController)
        }

        composable(route = Screens.Login.name) {
            LoginScreen(navController = navController)
        }

        composable(route = Screens.ForgotPassword.name) {
            ForgotPasswordScreen(navController = navController)
        }

        composable(route = Screens.Register.name) {
            RegisterScreen(navController = navController)
        }

        composable(route = Screens.BottomNav.name) {
            BottomNav(navigationController = navController)
        }

        composable(route = Screens.AddBook.name) {
            AddBookScreen(navController = navController)
        }

        composable(
            route = Screens.UpdateBook.name + "/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("id") ?: ""
            UpdateBookScreen(navController = navController, id = bookId)
        }

        composable(
            route = Screens.Details.name + "/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("id") ?: ""
            DetailsScreen(navController = navController, id = bookId)
        }
    }


}