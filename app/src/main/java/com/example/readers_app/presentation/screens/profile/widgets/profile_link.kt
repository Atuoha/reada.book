package com.example.readers_app.presentation.screens.profile.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileLink(title: String, hasDivider: Boolean = true, onClick: () -> Unit) {
    Text(text = title,
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.clickable {
            onClick()
        }
    )
    Spacer(modifier = Modifier.height(15.dp))
    if (hasDivider) HorizontalDivider(thickness = 0.3.dp)
}