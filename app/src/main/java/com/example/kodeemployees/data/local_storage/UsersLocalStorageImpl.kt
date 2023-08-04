package com.example.kodeemployees.data.local_storage

import com.example.kodeemployees.domain.models.User
import javax.inject.Inject

class UsersLocalStorageImpl @Inject constructor() : UsersLocalStorage {
    private var cachedUsersList = listOf<User>()

    override fun saveUsers(usersList: List<User>) {
        cachedUsersList = usersList
    }

    override  fun getCachedUsers(): List<User> = cachedUsersList

    override  fun getUser(userId: String): User? {
        return cachedUsersList.firstOrNull { it.id == userId }
    }
}