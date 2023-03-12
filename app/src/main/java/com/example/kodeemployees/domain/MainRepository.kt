package com.example.kodeemployees.domain

import com.example.kodeemployees.presentation.models.User

interface MainRepository {

    suspend fun getMockUsers(): List<User>
}