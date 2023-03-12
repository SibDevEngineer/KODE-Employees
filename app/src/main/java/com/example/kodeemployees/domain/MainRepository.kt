package com.example.kodeemployees.domain

import com.example.kodeemployees.presentation.screens.users.adapter.User

interface MainRepository {

    suspend fun getMockUsers(): List<User>
}