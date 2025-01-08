package com.example.readers_app.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun UsernameInput(
    username: MutableState<String>,
    usernameError: MutableState<String>
) {
    OutlinedTextField(
        value = username.value,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Username"
            )
        },
        placeholder = {
            Text(
                "John Doe",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        keyboardActions = KeyboardActions(
            onDone = {
                // Handle the keyboard done action
            }
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        onValueChange = {
            username.value = it

            if (username.value.length < 3) {
                usernameError.value = "Username must be at least 3 characters"
            } else {
                usernameError.value = ""
            }
        },
        isError = usernameError.value.isNotEmpty() && (username.value.isEmpty() || username.value.length < 3),
        label = { Text("Username") },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    )
    if (usernameError.value.isNotEmpty()) {
        Text(
            text = usernameError.value,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}