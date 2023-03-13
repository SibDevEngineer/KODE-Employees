package com.example.kodeemployees.data.repository

import com.example.kodeemployees.data.api.KodeApi
import com.example.kodeemployees.data.dto.toUser
import com.example.kodeemployees.data.models.DataSourceType
import com.example.kodeemployees.data.models.RequestParams
import com.example.kodeemployees.data.models.SortUsersType
import com.example.kodeemployees.domain.MainRepository
import com.example.kodeemployees.presentation.models.DepartmentType
import com.example.kodeemployees.presentation.models.User
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val kodeApi: KodeApi) : MainRepository {

    private var cachedUsersList = listOf<User>()

    private suspend fun getMockUsers(): List<User> {
        cachedUsersList = kodeApi.getMockUsers().usersList.map { it.toUser() }

        return cachedUsersList
    }

    override suspend fun getUsers(requestParams: RequestParams): List<User> {
        val usersList =
            if (requestParams.sourceType == DataSourceType.SERVER) getMockUsers()
            else cachedUsersList.toList()

        return usersList
            .filter { it.userName?.contains(requestParams.searchText) == true }
            .filter {
                if (requestParams.department == DepartmentType.ALL) true
                else it.department == requestParams.department.name.lowercase()
            }
            .sortedBy {
                when (requestParams.sortedBy) {
                    SortUsersType.ALPHABET -> it.userName
                    SortUsersType.BIRTHDATE -> it.birthday
                }
            }
    }
}



