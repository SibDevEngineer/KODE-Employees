package com.example.kodeemployees.data.local_storage

import com.example.kodeemployees.domain.models.User

interface UsersLocalStorage {
     fun saveUsers(usersList: List<User>)
     fun getCachedUsers(): List<User>
     fun getUser(userId: String): User?
}