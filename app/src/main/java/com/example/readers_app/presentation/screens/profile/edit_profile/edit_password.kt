package com.example.readers_app.presentation.screens.profile.edit_profile

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.readers_app.R
import com.example.readers_app.components.CustomBTN
import com.example.readers_app.components.PasswordInput
import com.example.readers_app.components.TopText
import com.example.readers_app.infrastructure.view_model.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditPasswordScreen(navController: NavController) {
    val userViewModel = hiltViewModel<UserViewModel>()
    val context = LocalContext.current

    val window = (context as Activity).window
    SideEffect {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color(0xFFFD9D48).toArgb()
    }

    val password = rememberSaveable { mutableStateOf("") }
    val passwordError = rememberSaveable { mutableStateOf("") }
    val isObscured = rememberSaveable { mutableStateOf(true) }

    val oldPassword = rememberSaveable { mutableStateOf("") }
    val oldPasswordError = rememberSaveable { mutableStateOf("") }
    val isOldPasswordObscured = rememberSaveable { mutableStateOf(true) }

    val valid = rememberSaveable(password.value, oldPassword.value) {
        password.value.trim()
            .isNotEmpty() && oldPassword.value.trim().isNotEmpty()
    }
    val loading = rememberSaveable { mutableStateOf(false) }
    val error = rememberSaveable { mutableStateOf("") }

    fun editPassword() {
        if (valid) {
            userViewModel.updatePassword(
                oldPassword.value,
                password.value,
                navController,
                context,
                loading,
                error,
            )
        } else {

            if (password.value.isEmpty()) {
                passwordError.value = "Password can not be empty"
            }

            if (oldPassword.value.isEmpty()) {
                oldPasswordError.value = "Old Password can not be empty"
            }

        }
    }

    return Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = Color.Transparent,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text(
                        "Change Password",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily.Serif,
                            letterSpacing = 1.sp
                        )
                    )
                })
        }) {
        Column(
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
                    TopText(
                        "Edit Password",
                        "Enter your old and new password to change your password"
                    )
                    Spacer(modifier = Modifier.height(40.dp))

                    PasswordInput(
                        oldPassword,
                        isOldPasswordObscured,
                        context,
                        window,
                        oldPasswordError,
                        labelAndHint = "Old Password"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    PasswordInput(
                        password,
                        isObscured,
                        context,
                        window,
                        passwordError,
                        labelAndHint = "New Password"
                    )

                    if (error.value.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(error.value, color = MaterialTheme.colorScheme.error)
                    }
                    Spacer(modifier = Modifier.height(40.dp))

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
                    if (!loading.value)
                        CustomBTN("Edit Password") {
                            editPassword()
                        }

                }


            }
        }
    }

}