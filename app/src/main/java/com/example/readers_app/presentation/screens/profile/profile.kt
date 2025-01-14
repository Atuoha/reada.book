package com.example.readers_app.presentation.screens.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.readers_app.infrastructure.view_model.UserViewModel
import com.example.readers_app.presentation.screens.profile.widgets.ConfirmDialog
import com.example.readers_app.presentation.screens.profile.widgets.ProfileImageSection
import com.example.readers_app.presentation.screens.profile.widgets.ProfileLink
import com.example.readers_app.presentation.screens.profile.widgets.TopTextSection

@Composable
fun ProfileScreen(navController: NavController) {
    val showLogoutDialog = remember { mutableStateOf(false) }
    val showDeleteAccountDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val userViewModel = hiltViewModel<UserViewModel>()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp, horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(10.dp))
        TopTextSection()
        Spacer(modifier = Modifier.height(20.dp))
        ProfileImageSection()
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
            showDeleteAccountDialog.value = true
        }
        Spacer(modifier = Modifier.height(15.dp))
        ProfileLink("Logout", hasDivider = false) {
            showLogoutDialog.value = true
        }

        if (showLogoutDialog.value) {
            ConfirmDialog(
                showLogoutDialog,
                "Logout",
                "Are you sure you want to logout?",
            ) {
                userViewModel.logout(navController, context)
            }
        }

        if (showDeleteAccountDialog.value) {
            ConfirmDialog(
                showDeleteAccountDialog,
                "Delete Account",
                "Are you sure you want to delete account?",
            ) {
                userViewModel.deleteAccount(navController, context)
            }
        }
    }
}


