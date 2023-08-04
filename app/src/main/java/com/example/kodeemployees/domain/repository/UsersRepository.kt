package com.example.kodeemployees.domain.repository

import com.example.kodeemployees.data.models.RequestParams
import com.example.kodeemployees.domain.models.User

interface UsersRepository {

    suspend fun getUsers(requestParams: RequestParams): List<User>
    suspend fun getLocalUser(userId: String): User?
}