package com.example.kodeemployees.presentation.screens.users.models

sealed class UserItemUi {
        data class User(
        val id: String,
        val avatarUrl: String?,
        val userName: String?,
        val userTag: String?,
        val profession: String?,
        val isShowBirthdate: Boolean,
        val birthdateUI: String?
    ) : UserItemUi()

    data class Header(val title: String) : UserItemUi()

    object Skeleton : UserItemUi()

    object EmptyListItem : UserItemUi()

    object UserNotFoundItem : UserItemUi()
}
