package com.example.kodeemployees.domain

import com.example.kodeemployees.data.models.RequestParams
import com.example.kodeemployees.data.models.SortUsersType
import com.example.kodeemployees.presentation.models.DepartmentType
import com.example.kodeemployees.presentation.models.User
import javax.inject.Inject

class UsersUseCase @Inject constructor(private val mainRepository: MainRepository) {

    suspend fun getUsers(requestParams: RequestParams): List<User> {
        return mainRepository.getUsers(requestParams)
            .filter {
                it.userName?.contains(requestParams.searchText, true) == true
                        || it.userTag?.contains(requestParams.searchText, true) == true
                        || it.phone?.contains(requestParams.searchText) == true
            }
            .filter {
                if (requestParams.department == DepartmentType.ALL) true
                else it.department == requestParams.department.name.lowercase()
            }
            .let { list ->
                when (requestParams.sortedBy) {
                    SortUsersType.ALPHABET -> list.sortedBy { it.userName }
                    SortUsersType.BIRTHDATE -> list.sortedBy { it.nextBirthdate }
                }
            }
    }
}