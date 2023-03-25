package com.example.kodeemployees.presentation.screens.users.adapter

import com.example.kodeemployees.presentation.models.User

sealed class UserItemUI {
    data class UserUI(
        val user: User,
        val isShowBirthdate: Boolean,
        val birthdateUI: String?
    ) : UserItemUI()

    data class HeaderUI(val title: String) : UserItemUI()

    object Skeleton : UserItemUI()
}
