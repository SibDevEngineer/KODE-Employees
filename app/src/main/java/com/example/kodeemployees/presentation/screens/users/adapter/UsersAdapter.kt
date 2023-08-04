package com.example.kodeemployees.presentation.screens.users.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.kodeemployees.presentation.screens.users.adapter.delegates.emptyListDelegate
import com.example.kodeemployees.presentation.screens.users.adapter.delegates.headerDelegate
import com.example.kodeemployees.presentation.screens.users.adapter.delegates.skeletonDelegate
import com.example.kodeemployees.presentation.screens.users.adapter.delegates.userItemDelegate
import com.example.kodeemployees.presentation.screens.users.adapter.delegates.userNotFoundDelegate
import com.example.kodeemployees.presentation.screens.users.models.UserItemUi
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import kotlin.jvm.internal.Intrinsics

class UsersAdapter(onUserClick: (userId: String) -> Unit) :
    AsyncListDifferDelegationAdapter<UserItemUi>(DiffCallback) {
    init {
        delegatesManager
            .addDelegate(userItemDelegate(onUserClick))
            .addDelegate(headerDelegate())
            .addDelegate(skeletonDelegate())
            .addDelegate(emptyListDelegate())
            .addDelegate(userNotFoundDelegate())
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<UserItemUi>() {
        override fun areItemsTheSame(oldItem: UserItemUi, newItem: UserItemUi): Boolean {
            return when {
                oldItem is UserItemUi.User && newItem is UserItemUi.User -> {
                    oldItem.id == newItem.id
                }

                else -> oldItem.javaClass == newItem.javaClass
            }
        }

        override fun areContentsTheSame(oldItem: UserItemUi, newItem: UserItemUi): Boolean {
            return Intrinsics.areEqual(oldItem, newItem)
        }
    }
}