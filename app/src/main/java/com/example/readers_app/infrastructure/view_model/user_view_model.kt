package com.example.readers_app.infrastructure.view_model

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.readers_app.core.app_strings.AppStrings
import com.example.readers_app.core.enums.Screens
import com.example.readers_app.domain.models.ReadaUser
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth) : ViewModel() {

    fun logout(navController: NavController, context: Context) {
        try {
            FirebaseAuth.getInstance().signOut().run {
                navController.navigate(Screens.Entry.name) {
                    popUpTo(0)
                }
                Toast.makeText(context, "Logout Successful", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Logout Failed. Please try again.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun isUserLoggedIn(): Boolean {
        firebaseAuth.currentUser?.let {
            return true
        } ?: run {
            return false
        }
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        navController: NavController,
        context: Context,
        loading: MutableState<Boolean>,
        error: MutableState<String>
    ) {
        error.value = ""
        loading.value = true

        try {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = task.result?.user
                        if (user != null) {
                            if (user.isEmailVerified) {
                                error.value = ""
                                loading.value = false
                                navController.navigate(Screens.BottomNav.name)
                                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                loading.value = false
                                error.value = "Please verify your email"
                                user.sendEmailVerification()
                            }
                        }
                    } else {
                        loading.value = false
                        error.value = task.exception?.message.orEmpty()
                    }
                }
        } catch (e: Exception) {
            error.value = e.message.toString()
            loading.value = false
        }


    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        username: String,
        navController: NavController,
        context: Context,
        loading: MutableState<Boolean>,
        error: MutableState<String>
    ) {
        error.value = ""
        loading.value = true

        try {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = task.result?.user
                        if (user != null) {

                            val readaUser =
                                ReadaUser(user.uid, username, email, AppStrings.AVATAR_URL)

                            // Save user details to Firestore
                            Firebase.firestore.collection("users").document(user.uid).set(
                                readaUser.toMap()
                            ).addOnCompleteListener { firestoreTask ->
                                if (firestoreTask.isSuccessful) {
                                    // Send email verification
                                    user.sendEmailVerification()
                                    error.value = ""
                                    loading.value = false
                                    navController.navigate(Screens.Login.name)
                                    Toast.makeText(
                                        context,
                                        "Registration Successful. Please verify your email.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    loading.value = false
                                    error.value =
                                        "Failed to save user data: ${firestoreTask.exception?.message}"
                                }
                            }
                        }
                    } else {
                        loading.value = false
                        error.value = task.exception?.message.orEmpty()
                    }
                }
        } catch (e: Exception) {
            error.value = e.message.toString()
            loading.value = false
        }


    }


    fun resetPassword(
        email: String,
        navController: NavController,
        context: Context,
        loading: MutableState<Boolean>,
        error: MutableState<String>
    ) {
        loading.value = true
        error.value = ""

        try {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        error.value = ""
                        loading.value = false
                        navController.navigate(Screens.Login.name)
                        Toast.makeText(context, "Forgot password link sent", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        loading.value = false
                        error.value = task.exception?.localizedMessage.orEmpty()
                    }
                }
        } catch (e: Exception) {
            error.value = e.message.toString()
            loading.value = false
            Toast.makeText(context, "Forgot password failed", Toast.LENGTH_SHORT).show()

        }

    }


    fun deleteAccount(navController: NavController, context: Context) {
        try {
            firebaseAuth.currentUser?.delete()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Firebase.firestore.collection("users").document(firebaseAuth.currentUser!!.uid)
                        .delete().addOnCompleteListener { deleteTask ->
                            if (deleteTask.isSuccessful) {
                                navController.navigate(Screens.Entry.name) {
                                    popUpTo(0)
                                }
                                Toast.makeText(context, "Account Deleted", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Account Deletion Failed. Please try again.",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    fun updatePassword(
        password: String,
        navController: NavController,
        context: Context,
        loading: MutableState<Boolean>,
        error: MutableState<String>,
    ) {
        loading.value = true
        error.value = ""
        firebaseAuth.currentUser?.updatePassword(password)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                error.value = ""
                loading.value = false
                navController.popBackStack()
                Toast.makeText(context, "Password Updated", Toast.LENGTH_SHORT).show()
            } else {
                loading.value = false
                error.value = task.exception?.localizedMessage.orEmpty()
                Toast.makeText(context, "Password Update Failed", Toast.LENGTH_SHORT).show()

            }
        }
    }


    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun updateProfile(
        username: String, email: String, image: String,
        navController: NavController,
        context: Context,
        loading: MutableState<Boolean>,
        error: MutableState<String>,
    ) {
        loading.value = true
        error.value = ""

        firebaseAuth.currentUser?.verifyBeforeUpdateEmail(email)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {

                var downloadUrl: String

                // fetch user data from firestore
                Firebase.firestore.collection("users").document(firebaseAuth.currentUser!!.uid)
                    .get().addOnSuccessListener {
                        if (it.exists()) {
                            val user = it.toObject(ReadaUser::class.java)
                            if (user != null) {
                                downloadUrl = user.avatarUrl
                            }
                        }

                        val storageRef =
                            Firebase.storage.reference.child("users/${firebaseAuth.currentUser!!.uid}/profile.jpg")

                        // upload image to firebase storage
                        val uploadTask = if (image.isNotEmpty()) {
                            storageRef.putFile(image.toUri())
                        } else {
                            null
                        }

                        uploadTask?.addOnSuccessListener { uploadFile ->
                            if (uploadFile.task.isSuccessful) {
                                storageRef.downloadUrl.addOnSuccessListener { uri ->
                                    downloadUrl = uri.toString()

                                    // update user data in firestore
                                    Firebase.firestore.collection("users")
                                        .document(firebaseAuth.currentUser!!.uid)
                                        .update(
                                            mapOf(
                                                "email" to email,
                                                "username" to username,
                                                "avatar" to downloadUrl
                                            )
                                        ).addOnCompleteListener { updateUserTask ->
                                            if (updateUserTask.isSuccessful) {
                                                // update user data in firebase auth
                                                val profileUpdates =
                                                    UserProfileChangeRequest.Builder()
                                                        .setDisplayName(username)
                                                        .setPhotoUri(uri)
                                                        .build()

                                                // update user data in firebase auth
                                                firebaseAuth.currentUser?.updateProfile(
                                                    profileUpdates
                                                )
                                                    ?.addOnCompleteListener { updateTask ->
                                                        if (updateTask.isSuccessful) {
                                                            error.value = ""
                                                            loading.value = false
                                                            navController.popBackStack()
                                                            Toast.makeText(
                                                                context,
                                                                "Profile Updated",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                                .show()
                                                        }else{
                                                            loading.value = false
                                                            error.value = updateTask.exception?.localizedMessage.orEmpty()
                                                        }
                                                    }

                                            }else{
                                                loading.value = false
                                                error.value = updateUserTask.exception?.localizedMessage.orEmpty()
                                            }
                                        }

                                }
                            }else{
                                loading.value = false
                                error.value = uploadFile.task.exception?.localizedMessage.orEmpty()
                            }
                        }


                    }
            } else {
                loading.value = false
                error.value = task.exception?.localizedMessage.orEmpty()
            }
        }
    }


}