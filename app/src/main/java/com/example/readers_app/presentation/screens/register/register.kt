package com.example.readers_app.presentation.screens.register

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.example.readers_app.R
import com.example.readers_app.components.CustomBTN
import com.example.readers_app.components.EmailInput
import com.example.readers_app.components.PasswordInput
import com.example.readers_app.components.RichTextNav
import com.example.readers_app.components.TopText
import com.example.readers_app.components.UsernameInput
import com.example.readers_app.core.app_strings.AppStrings
import com.example.readers_app.core.enums.Screens
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current

    val window = (context as Activity).window
    SideEffect {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color(0xFFFD9D48).toArgb()
    }

    val username = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val emailError = rememberSaveable { mutableStateOf("") }
    val passwordError = rememberSaveable { mutableStateOf("") }
    val usernameError = rememberSaveable { mutableStateOf("") }
    val isObscured = rememberSaveable { mutableStateOf(true) }
    val valid = remember(email.value, password.value, username.value) {
        email.value.trim().isNotEmpty() && password.value.trim()
            .isNotEmpty() && username.value.trim().isNotEmpty()
    }
    val loading = remember { mutableStateOf(false) }
    val error = remember { mutableStateOf("") }

    fun register() {
        if (valid) {
            error.value = ""
            loading.value = true
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.value, password.value)
                .addOnCompleteListener {
                    if (it.isSuccessful) {

                        val user = it.result.user

                        Firebase.firestore.collection("users").document(user!!.uid).set(
                            mapOf(
                                "username" to username.value, "email" to email.value,
                                "userId" to user.uid, "avatar" to AppStrings.AVATAR_URL
                            )
                        )
                        // send email verification
                        user.sendEmailVerification()

                        error.value = ""
                        loading.value = false
                        navController.navigate(Screens.Login.name)
                        Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                    } else {
                        loading.value = false
                        error.value = it.exception?.message.toString()
                    }
                }
        } else {
            if (email.value.isEmpty()) {
                emailError.value = "Email can not be empty"
            }

            if (password.value.isEmpty()) {
                passwordError.value = "Password can not be empty"
            }

            if (username.value.isEmpty()) {
                usernameError.value = "Username can not be empty"
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
                modifier = Modifier
                    .padding(
                        horizontal = 25.dp,
                        vertical = 28.dp
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                TopText("Register", "Please fill in the details to register")
                Spacer(modifier = Modifier.height(40.dp))
                UsernameInput(username, usernameError)
                Spacer(modifier = Modifier.height(10.dp))
                EmailInput(email, emailError)
                Spacer(modifier = Modifier.height(10.dp))
                PasswordInput(password, isObscured, context, window, passwordError)

                if (error.value.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(error.value, color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.height(40.dp))
                RichTextNav("By continuing to register you agree to our ", "Terms and Conditions")

                if (loading.value) {
                    Spacer(modifier = Modifier.height(10.dp))
                    CircularProgressIndicator(
                        modifier = Modifier
                            .width(64.dp)
                            .align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }

                Spacer(modifier = Modifier.height(80.dp))
                if(!loading.value)
                CustomBTN("Register") {
                    register()
                }
                Spacer(modifier = Modifier.height(10.dp))
                RichTextNav("Already own an account? ", "Login") {
                    navController.navigate(Screens.Login.name)
                }
            }


        }
    }

}




