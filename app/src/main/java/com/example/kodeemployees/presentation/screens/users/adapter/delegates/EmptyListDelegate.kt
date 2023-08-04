package com.example.kodeemployees.presentation.screens.users.adapter.delegates

import com.example.kodeemployees.databinding.ViewItemEmptyListBinding
import com.example.kodeemployees.presentation.screens.users.models.UserItemUi
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun emptyListDelegate() =
    adapterDelegateViewBinding<UserItemUi.EmptyListItem, UserItemUi, ViewItemEmptyListBinding>(
        { layoutInflater, parent ->
            ViewItemEmptyListBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        }
    ) { }