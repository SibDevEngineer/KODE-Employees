package com.example.kodeemployees.presentation.screens.users.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import kotlin.jvm.internal.Intrinsics

class UsersAdapter(onUserClick: (user: User) -> Unit) :
    AsyncListDifferDelegationAdapter<User>(DiffCallback) {
    init {
        delegatesManager
            .addDelegate(userItemDelegate(onUserClick))
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return Intrinsics.areEqual(oldItem, newItem)
        }
    }
}