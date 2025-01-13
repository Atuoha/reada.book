package com.example.readers_app.presentation.screens.update_book.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextInputField(
    isSingleLine: Boolean,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction,
    icon: ImageVector,
    onAction: KeyboardActions = KeyboardActions.Default,
    placeholder: String,
) {
    return OutlinedTextField(
        value = valueState.value,
        onValueChange = { newValue ->
             valueState.value = newValue
        },
        placeholder = {Text(placeholder)},
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (!isSingleLine) {
                    Modifier.heightIn(min = (4 * 24).dp)
                } else Modifier
            ),
        label = { Text(text = labelId) },
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        singleLine = isSingleLine,
        maxLines = if (isSingleLine) 1 else {
            Int.MAX_VALUE
        },
        keyboardActions = onAction,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Icon",
                tint = Color.LightGray
            )
        },
        textStyle = TextStyle(
            color =
            MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp
        ),
        shape = RoundedCornerShape(10.dp)
    )

}