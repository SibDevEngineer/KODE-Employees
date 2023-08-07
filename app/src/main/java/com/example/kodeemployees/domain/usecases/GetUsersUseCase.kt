package com.example.kodeemployees.domain.usecases

import com.example.kodeemployees.data.models.RequestParams
import com.example.kodeemployees.data.models.SortUsersType
import com.example.kodeemployees.domain.repository.UsersRepository
import com.example.kodeemployees.domain.models.Department
import com.example.kodeemployees.domain.models.User
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(requestParams: RequestParams): List<User> {
        return usersRepository.getUsers(requestParams)
            .filter {
                it.userName?.contains(requestParams.searchText, true) == true
                        || it.userTag?.contains(requestParams.searchText, true) == true
                        || it.phone?.contains(requestParams.searchText) == true
            }
            .filter {
                if (requestParams.department == Department.ALL) true
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