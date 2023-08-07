package com.example.kodeemployees.presentation.screens.users.adapter.delegates

import com.example.kodeemployees.databinding.ViewItemHeaderBirthdateBinding
import com.example.kodeemployees.presentation.screens.users.models.UserItemUi
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun headerDelegate() =
    adapterDelegateViewBinding<UserItemUi.Header, UserItemUi, ViewItemHeaderBirthdateBinding>(
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