package com.example.kodeemployees.data.local_storage

import com.example.kodeemployees.presentation.models.User
import javax.inject.Inject

class LocalStorage @Inject constructor() {
    private var cachedUsersList = listOf<User>()


    fun saveUsers(usersList: List<User>) {
        cachedUsersList = usersList
    }

    fun getCachedUsers(): List<User> = cachedUsersList
}