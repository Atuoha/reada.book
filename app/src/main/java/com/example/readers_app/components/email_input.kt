package com.example.readers_app.components

import android.content.Context
import android.util.Patterns
import android.view.Window
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
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
 fun EmailInput(
    email: MutableState<String>,
    emailError: MutableState<String>,
    isForgotPassword: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
    onDone: () -> Unit = {}
) {
    OutlinedTextField(
        value = email.value,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email"
            )
        },
        placeholder = {
            Text(
                "johndoe@gmail.com",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        keyboardActions = KeyboardActions(
            onDone = {
               if(isForgotPassword){
                     onDone()
               }
            }
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction
        ),
        onValueChange = {
            email.value = it
            if (!Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
                emailError.value = "Invalid email"
            } else {
                emailError.value = ""
            }

        },
        isError = emailError.value.isNotEmpty() && (email.value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(
            email.value
        ).matches()),
        label = { Text("Email") },
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    )
    if (emailError.value.isNotEmpty()) {
        Text(
            text = emailError.value,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}