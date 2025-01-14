package com.example.readers_app.infrastructure.view_model

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.readers_app.core.app_strings.AppStrings
import com.example.readers_app.core.enums.Screens
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth) : ViewModel() {

    fun logout(navController: NavController, context: Context) {
        FirebaseAuth.getInstance().signOut().run {
            navController.navigate(Screens.Entry.name) {
                popUpTo(0)
            }
            Toast.makeText(context, "Logout Successful", Toast.LENGTH_SHORT).show()
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
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    if (user != null) {
                        if (user.isEmailVerified) {
                            error.value = ""
                            loading.value = false
                            navController.navigate(Screens.BottomNav.name)
                            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
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
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    if (user != null) {
                        // Save user details to Firestore
                        Firebase.firestore.collection("users").document(user.uid).set(
                            mapOf(
                                "username" to username,
                                "email" to email,
                                "userId" to user.uid,
                                "avatar" to AppStrings.AVATAR_URL
                            )
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
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    error.value = ""
                    loading.value = false
                    navController.navigate(Screens.Login.name)
                    Toast.makeText(context, "Forgot password link sent", Toast.LENGTH_SHORT).show()
                } else {
                    loading.value = false
                    error.value = task.exception?.localizedMessage.orEmpty()
                }
            }
    }





    fun deleteAccount(navController: NavController, context: Context) {
        firebaseAuth.currentUser?.delete()?.addOnCompleteListener { task ->

            if (task.isSuccessful) {

                Firebase.firestore.collection("users").document(firebaseAuth.currentUser!!.uid).delete().addOnCompleteListener { deleteTask ->
                    if (deleteTask.isSuccessful) {
                        navController.navigate(Screens.Entry.name) {
                            popUpTo(0)
                        }
                        Toast.makeText(context, "Account Deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun updateEmail(email: String, onResult: (Boolean) -> Unit) {
        firebaseAuth.currentUser?.verifyBeforeUpdateEmail(email)
            ?.addOnCompleteListener { task ->
                onResult(task.isSuccessful)
            }

    }


    fun updatePassword(password: String, onResult: (Boolean) -> Unit) {
        firebaseAuth.currentUser?.updatePassword(password)
            ?.addOnCompleteListener { task ->
                onResult(task.isSuccessful)
            }
    }

    fun updateProfile(name: String, email: String, onResult: (Boolean) -> Unit) {
        firebaseAuth.currentUser?.let {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
        }
    }


}