package com.example.readers_app.domain.data

sealed class Resource<T>(
    val data: T? = null,
    val error: Exception? = null,
    val loading: Boolean = false
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(error: Exception, data: T? = null) : Resource<T>(data, error)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}