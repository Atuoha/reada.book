package com.example.readers_app.presentation.screens.profile.edit_profile

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.readers_app.components.CustomBTN
import com.example.readers_app.components.EmailInput
import com.example.readers_app.components.TopText
import com.example.readers_app.components.UsernameInput
import com.example.readers_app.core.utils.saveBitmapToFile
import com.example.readers_app.infrastructure.view_model.UserViewModel
import com.example.readers_app.presentation.screens.profile.edit_profile.widgets.SelectImageSource
import com.example.readers_app.ui.theme.primary

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditProfileScreen(navController: NavController) {
    val userViewModel = hiltViewModel<UserViewModel>()
    val user = userViewModel.user.value
    val context = LocalContext.current
    val showImageSelectionDialog = remember { mutableStateOf(false) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
        imageBitmap = null
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            imageBitmap = bitmap.asImageBitmap()
            imageUri = null
        }
    }

    val window = (context as Activity).window
    SideEffect {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color(0xFFFD9D48).toArgb()
    }

    val imageURL = remember{ mutableStateOf("") }
    val username = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    val emailError = rememberSaveable { mutableStateOf("") }
    val usernameError = rememberSaveable { mutableStateOf("") }
    val valid = remember(email.value, username.value) {
        email.value.trim().isNotEmpty() && username.value.trim().isNotEmpty()
    }
    val loading = remember { mutableStateOf(false) }
    val error = remember { mutableStateOf("") }


    LaunchedEffect(user) {
      if(user != null){
          email.value = user.email.toString()
          username.value = user.username.toString()
          imageURL.value = user.avatar.toString()
      }

    }

    fun editProfile() {
        if (valid) {
            val imagePath = when {
                imageUri != null -> {
                    imageUri.toString()
                }

                imageBitmap != null -> {
                    val file = saveBitmapToFile(context, imageBitmap!!)
                    file?.toURI()?.toString() ?: ""
                }

                else -> {
                    ""
                }
            }
            userViewModel.updateProfile(
                username = username.value.trim(),
                email = email.value.trim(),
                image = imagePath,
                navController = navController,
                context = context,
                loading = loading,
                error = error
            )


        } else {
            if (email.value.isEmpty()) {
                emailError.value = "Email can not be empty"
            }


            if (username.value.isEmpty()) {
                usernameError.value = "Username can not be empty"
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
                        "Edit Profile",
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
            Spacer(modifier = Modifier.height(50.dp))
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .border(width = 2.dp, color = Color.White, shape = CircleShape)
                    .align(alignment = Alignment.CenterHorizontally)
                    .clipToBounds(),
                contentAlignment = Alignment.Center
            ) {

                when {
                    imageBitmap != null -> {
                        Image(
                            bitmap = imageBitmap!!,
                            contentDescription = "Selected Image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                    }

                    imageUri != null -> {
                        Image(
                            painter = rememberAsyncImagePainter(imageUri),
                            contentDescription = "Selected Image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                    }

                    else -> {
                        AsyncImage(model = imageURL.value, contentDescription = "Profile Image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                            )
                    }
                }




                Box(
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                        .align(Alignment.BottomEnd)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable {
                            showImageSelectionDialog.value = true
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Icon",
                        tint = primary,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(18.dp)
                            .offset(y = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

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
                    TopText("Edit Profile", "Please fill in the details to edit your profile")
                    Spacer(modifier = Modifier.height(40.dp))


                    UsernameInput(username, usernameError)
                    Spacer(modifier = Modifier.height(10.dp))
                    EmailInput(email, emailError)

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
                        CustomBTN("Edit Profile") {
                            editProfile()
                        }

                }


                if (showImageSelectionDialog.value) {
                    SelectImageSource(
                        showImageSelectionDialog,
                        selectPhotoImage = {
                            galleryLauncher.launch("image/*")
                            showImageSelectionDialog.value = false
                        },
                        selectCameraImage = {
                            cameraLauncher.launch(null)
                            showImageSelectionDialog.value = false
                        }
                    )
                }
            }
        }
    }

}