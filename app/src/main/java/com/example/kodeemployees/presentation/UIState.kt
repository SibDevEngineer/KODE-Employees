package com.example.kodeemployees.presentation

sealed class UIState {
    /** состояние по умолчанию*/
    object Default : UIState()

    /** состояние в процессе загрузки*/
    object Loading : UIState()

    /** состояние в процессе загрузки*/
    object Refreshing : UIState()

    /** состояние, когда нужный пользователь не найден*/
    object UserNotFound : UIState()

    /** состояние при возникновении ошибок */
    data class Error(val text: String?) : UIState()
}
