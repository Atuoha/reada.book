package com.example.readers_app.presentation.screens.profile.widgets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun ConfirmDialog(
    showLogoutDialog: MutableState<Boolean>,
    title: String,
    text: String,
    logout: () -> Unit,

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
        title = { Text(title) },
        text = { Text(text) }
    )
}