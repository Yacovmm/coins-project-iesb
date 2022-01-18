package com.example.iesbcoinapp.core.utils

sealed class ViewState {
    data class Success<T>(val result: T) : ViewState()
    data class Error(val msg: String) : ViewState()
    object Loading : ViewState()
    object NoState : ViewState()
}
