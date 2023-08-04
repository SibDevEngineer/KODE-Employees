package com.example.kodeemployees.data.repository

import com.example.kodeemployees.core.coroutines.AppDispatchers
import com.example.kodeemployees.data.api.RemoteApi
import com.example.kodeemployees.data.local_storage.UsersLocalStorage
import com.example.kodeemployees.data.mapper.UserDtoMapper
import com.example.kodeemployees.data.models.DataSourceType
import com.example.kodeemployees.data.models.RequestParams
import com.example.kodeemployees.domain.models.User
import com.example.kodeemployees.domain.repository.UsersRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val remoteApi: RemoteApi,
    private val usersLocalStorage: UsersLocalStorage,
    private val appDispatchers: AppDispatchers,
    private val userDtoMapper: UserDtoMapper
) : UsersRepository {

    override suspend fun getUsers(requestParams: RequestParams): List<User> {
        return if (requestParams.sourceType == DataSourceType.SERVER) getMockUsers()
        else getCachedUsers()
    }

    override suspend fun getLocalUser(userId: String): User? = usersLocalStorage.getUser(userId)

    private suspend fun getMockUsers(): List<User> {
        return withContext(appDispatchers.io) {
            remoteApi.getMockUsers().usersList
        }
            .map { userDtoMapper.mapToUser(it) }
            .also { usersLocalStorage.saveUsers(it) }
    }

    private fun getCachedUsers() = usersLocalStorage.getCachedUsers()
}



