package com.example.kodeemployees.presentation.screens.users.adapter

import com.example.kodeemployees.databinding.ViewItemHeaderBirthdateBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun headerDelegate() =
    adapterDelegateViewBinding<UserItemUI.HeaderUI, UserItemUI, ViewItemHeaderBirthdateBinding>(
        { layoutInflater, parent ->
            ViewItemHeaderBirthdateBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        }
    ) {

        bind {
            binding.vHeaderTitle.text = item.title
        }
    }