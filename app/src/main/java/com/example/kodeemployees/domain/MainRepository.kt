package com.example.kodeemployees.domain

import com.example.kodeemployees.data.models.RequestParams
import com.example.kodeemployees.presentation.models.User

interface MainRepository {

    suspend fun getUsers(requestParams: RequestParams): List<User>
}