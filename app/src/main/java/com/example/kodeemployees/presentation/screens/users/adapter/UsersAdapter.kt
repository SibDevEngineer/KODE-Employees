package com.example.kodeemployees.presentation.screens.users.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.kodeemployees.presentation.models.User
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import kotlin.jvm.internal.Intrinsics

class UsersAdapter(onUserClick: (User) -> Unit) :
    AsyncListDifferDelegationAdapter<UserItemUI>(DiffCallback) {
    init {
        delegatesManager
            .addDelegate(userItemDelegate(onUserClick))
            .addDelegate(headerDelegate())
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<UserItemUI>() {
        override fun areItemsTheSame(oldItem: UserItemUI, newItem: UserItemUI): Boolean {
            return oldItem.javaClass == newItem.javaClass
        }

        override fun areContentsTheSame(oldItem: UserItemUI, newItem: UserItemUI): Boolean {
            return Intrinsics.areEqual(oldItem, newItem)
        }
    }
}