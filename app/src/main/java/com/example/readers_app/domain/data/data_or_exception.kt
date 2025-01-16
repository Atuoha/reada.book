package com.example.readers_app.domain.data

class DataOrException<T, Boolean, Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var error: Exception? = null
)
