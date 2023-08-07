package com.example.kodeemployees.presentation.screens.users

sealed class ScreenState<T> {
    data class Error<T>(val text: String?) : ScreenState<T>()

    data class Data<T>(
        val data: T,
        val updating: Boolean = false
    ) : ScreenState<T>()
}


