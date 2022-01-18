package com.example.iesbcoinapp.core.utils

sealed class ResponseWrapper<out T> {
    data class Success<T>(val result: T) : ResponseWrapper<T>()
    data class Error(val errorResponse: String) : ResponseWrapper<Nothing>()
}
