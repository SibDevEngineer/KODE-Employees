package com.example.kodeemployees.presentation.screens.users.adapter

import com.example.kodeemployees.databinding.ViewSkeletonUserItemBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun skeletonDelegate() =
    adapterDelegateViewBinding<UserItemUI.Skeleton, UserItemUI, ViewSkeletonUserItemBinding>(
        { layoutInflater, parent ->
            ViewSkeletonUserItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        }
    ) { }