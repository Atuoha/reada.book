package com.example.readers_app.presentation.screens.profile.edit_profile.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectImageSource(
    showLogoutDialog: MutableState<Boolean>,
    selectPhotoImage: () -> Unit,
    selectCameraImage: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { showLogoutDialog.value = false },
        confirmButton = {
            Button(onClick = {
                selectCameraImage()
                showLogoutDialog.value = false
            }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(imageVector = Icons.Default.Camera, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Camera")
                }
            }
        },
        dismissButton = {
            Button(onClick = {
                selectPhotoImage()
                showLogoutDialog.value = false
            }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(imageVector = Icons.Default.Photo, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Gallery")
                }
            }
        },
        title = { Text("Select Image Source") },
        text = { Text("Please select the source for selecting the image.") }
    )
}