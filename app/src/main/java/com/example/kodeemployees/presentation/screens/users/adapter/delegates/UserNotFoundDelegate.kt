package com.example.kodeemployees.presentation.screens.users.adapter.delegates

import com.example.kodeemployees.databinding.ViewItemUserNotFoundBinding
import com.example.kodeemployees.presentation.screens.users.models.UserItemUi
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun userNotFoundDelegate() =
    adapterDelegateViewBinding<UserItemUi.UserNotFoundItem, UserItemUi, ViewItemUserNotFoundBinding>(
        { layoutInflater, parent ->
            ViewItemUserNotFoundBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        }
    ) { }