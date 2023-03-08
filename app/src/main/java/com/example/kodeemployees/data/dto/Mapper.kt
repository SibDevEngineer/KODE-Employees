package com.example.kodeemployees.data.dto

import com.example.kodeemployees.presentation.screens.users.adapter.User

fun UserDto.toUser(): User {
    return User(
        id = id,
        avatarUrl = avatarUrl,
        firstName = firstName,
        lastName = lastName,
        userTag = userTag,
        department = department,
        position = position,
        birthday = birthday,
        phone = phone
    )
}