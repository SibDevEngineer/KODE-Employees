package com.example.kodeemployees.presentation.screens.users.adapter

import com.example.kodeemployees.databinding.ViewUserItemBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun userItemDelegate(onUserClick: (user: User) -> Unit) =
    adapterDelegateViewBinding<User, User, ViewUserItemBinding>(
        { layoutInflater, parent ->
            ViewUserItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        }
    ) {
        binding.vItemUser.setOnClickListener {
            onUserClick(item)
        }

        bind {
            with(binding) {

            }
        }
    }