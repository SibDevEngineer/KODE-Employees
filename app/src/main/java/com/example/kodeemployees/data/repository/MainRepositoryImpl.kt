package com.example.kodeemployees.data.repository

import com.example.kodeemployees.data.api.KodeApi
import com.example.kodeemployees.data.dto.toUser
import com.example.kodeemployees.data.local_storage.LocalStorage
import com.example.kodeemployees.data.models.DataSourceType
import com.example.kodeemployees.data.models.RequestParams
import com.example.kodeemployees.domain.MainRepository
import com.example.kodeemployees.presentation.models.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val kodeApi: KodeApi,
    private val localStorage: LocalStorage,
    private val dispatcherIO: CoroutineDispatcher
) : MainRepository {

    private suspend fun getMockUsers(): List<User> {
        return withContext(dispatcherIO) {
            val usersList = kodeApi.getMockUsers().usersList.map { it.toUser() }
            localStorage.saveUsers(usersList)
            return@withContext usersList
        }
    }

    private fun getCachedUsers() = localStorage.getCachedUsers()

    override suspend fun getUsers(requestParams: RequestParams): List<User> {

        return if (requestParams.sourceType == DataSourceType.SERVER) getMockUsers()
        else getCachedUsers()
    }
}



