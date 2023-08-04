package com.example.kodeemployees.presentation.screens.users.adapter.delegates

import com.example.kodeemployees.databinding.ViewItemSkeletonUserBinding
import com.example.kodeemployees.presentation.screens.users.models.UserItemUi
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun skeletonDelegate() =
    adapterDelegateViewBinding<UserItemUi.Skeleton, UserItemUi, ViewItemSkeletonUserBinding>(
        { layoutInflater, parent ->
            ViewItemSkeletonUserBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        }
    ) { }