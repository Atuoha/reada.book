package com.example.readers_app.di

import com.example.readers_app.core.constants.AppConstants
import com.example.readers_app.infrastructure.network.BookApi
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.google.firebase.storage.FirebaseStorage

@Module
@InstallIn(SingletonComponent::class)
object AppBundle {

    @Provides
    @Singleton
    fun providesBooks(): BookApi {
        return Retrofit.Builder().baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(BookApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFirebase(): Firebase = Firebase


    @Provides
    @Singleton
    fun providesFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

}