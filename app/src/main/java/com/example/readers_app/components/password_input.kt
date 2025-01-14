package com.example.readers_app.components

import android.app.Activity
import android.content.Context
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
 fun PasswordInput(
    password: MutableState<String>,
    isObscured: MutableState<Boolean>,
    context: Context,
    window: Window,
    passwordError: MutableState<String>,
    labelAndHint: String = "Password",
) {
    OutlinedTextField(
        value = password.value,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Key,
                contentDescription = "Password"
            )
        },

        trailingIcon = {
            if (password.value.isNotEmpty()) Icon(
                imageVector = if (isObscured.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                contentDescription = "Toggle password visibility",
                modifier = Modifier.clickable {
                    isObscured.value = !isObscured.value
                }
            )
        },

        visualTransformation = if (isObscured.value) PasswordVisualTransformation() else VisualTransformation.None,
        placeholder = {
            Text(
                labelAndHint,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        keyboardActions = KeyboardActions(
            onDone = {
                context.getSystemService(Activity.INPUT_METHOD_SERVICE)
                    ?.let { imm ->
                        (imm as InputMethodManager).hideSoftInputFromWindow(
                            window.decorView.windowToken,
                            0
                        )
                    }
            }
        ),

        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        onValueChange = {
            password.value = it
            if (password.value.length < 8) {
                passwordError.value = "Password must be at least 8 characters"
            } else {
                passwordError.value = ""
            }
        },
        isError = passwordError.value.isNotEmpty() && (password.value.isEmpty() || password.value.length < 8),
        label = { Text(labelAndHint) },
        modifier = Modifier
            .fillMaxWidth(),

        shape = RoundedCornerShape(10.dp)
    )
    if (passwordError.value.isNotEmpty()) {
        Text(
            text = passwordError.value,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
