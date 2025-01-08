package com.example.readers_app.presentation.screens.forgot_password

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.example.readers_app.R
import com.example.readers_app.components.CustomBTN
import com.example.readers_app.components.EmailInput
import com.example.readers_app.components.RichTextNav
import com.example.readers_app.components.TopText

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    val context = LocalContext.current
    val window = (context as Activity).window
    SideEffect {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color(0xFFFD9D48).toArgb()
    }

    val email = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf("") }

    fun forgotPassword() {
        if (email.value.isNotEmpty()) {
            // Handle forgot password
        } else {
            if (email.value.isEmpty()) {
                emailError.value = "Email can not be empty"
            }
        }
    }

    return Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ico), contentDescription = "Icon",
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Box(

            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                )
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = 25.dp,
                    vertical = 28.dp
                ) .verticalScroll(rememberScrollState())
            ) {
                TopText("Forgot Password", "Enter your email to reset password")
                Spacer(modifier = Modifier.height(40.dp))
                EmailInput(
                    email = email,
                    emailError = emailError,
                    imeAction = ImeAction.Done,
                    isForgotPassword = true
                ) {
                    context.getSystemService(Activity.INPUT_METHOD_SERVICE)?.let { imm ->
                        (imm as InputMethodManager).hideSoftInputFromWindow(
                            window.decorView.windowToken,
                            0
                        )
                    }
                }
                Spacer(modifier = Modifier.height(220.dp))
                CustomBTN("Forgot Password") {
                    forgotPassword()
                }
                Spacer(modifier = Modifier.height(10.dp))
                RichTextNav("Remembered password? ", "Login") {
                    navController.navigate("login")
                }
            }


        }
    }

}