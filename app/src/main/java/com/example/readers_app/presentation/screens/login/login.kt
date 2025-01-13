package com.example.readers_app.presentation.screens.login

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.example.readers_app.R
import com.example.readers_app.components.CustomBTN
import com.example.readers_app.components.EmailInput
import com.example.readers_app.components.PasswordInput
import com.example.readers_app.components.RichTextNav
import com.example.readers_app.components.TopText
import com.example.readers_app.core.enums.Screens
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current

    val window = (context as Activity).window
    SideEffect {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color(0xFFFD9D48).toArgb()
    }


    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordError = rememberSaveable { mutableStateOf("") }
    val emailError = rememberSaveable { mutableStateOf("") }
    val isObscured = rememberSaveable { mutableStateOf(true) }
    val loading = remember { mutableStateOf(false) }
    val error = remember { mutableStateOf("") }
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    fun login() {
        if (valid) {
            // Handle login
            loading.value = true
            error.value = ""
           FirebaseAuth.getInstance().signInWithEmailAndPassword(email.value, password.value).addOnCompleteListener {
               if(it.isSuccessful){
                   val user = it.result.user
                   if (user != null) {
                       if(user.isEmailVerified){
                           error.value = ""
                           loading.value = false
                           navController.navigate(Screens.BottomNav.name)
                       }else{
                           loading.value = false
                           error.value = "Please verify your email"
                           user.sendEmailVerification()
                       }
                   }


               }else{
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
                TopText("Login", "Please fill in the details to login")
                Spacer(modifier = Modifier.height(40.dp))
                EmailInput(email, emailError)
                Spacer(modifier = Modifier.height(10.dp))
                PasswordInput(password, isObscured, context, window, passwordError)
                if (error.value.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(error.value, color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Forgot password?",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate(Screens.ForgotPassword.name) }
                )
                if(loading.value){
                    Spacer(modifier = Modifier.height(30.dp))
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp).align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
                Spacer(modifier = Modifier.height(110.dp))
                CustomBTN("Login") {
                    login()
                }
                Spacer(modifier = Modifier.height(10.dp))
                RichTextNav("New to Reada? ", "Register") {
                    navController.navigate(Screens.Register.name)
                }
            }


        }
    }


}





