package com.example.readers_app.presentation.screens.profile.widgets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun ConfirmLogout(
    showLogoutDialog: MutableState<Boolean>,
    logout: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { showLogoutDialog.value = false },
        confirmButton = {
            Button(onClick = { logout() }) {
                Text("Proceed")
            }
        },
        dismissButton = {
            Button(onClick = {
                showLogoutDialog.value = false
            }) {
                Text("Cancel")
            }
        },
        title = { Text("Logout") },
        text = { Text("Are you sure you want to logout?") }
    )
}