package com.example.kodeemployees.data.repository

import com.example.kodeemployees.data.api.KodeApi
import com.example.kodeemployees.data.dto.toUser
import com.example.kodeemployees.domain.MainRepository
import com.example.kodeemployees.presentation.screens.users.adapter.User
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val kodeApi: KodeApi) : MainRepository {

    private var cachedUsersList = listOf<User>()

    override suspend fun getMockUsers(): List<User> {
        cachedUsersList = kodeApi.getMockUsers().usersList.map { it.toUser() }

        return cachedUsersList
    }
}