package com.example.kodeemployees.domain.usecases

import com.example.kodeemployees.domain.models.User
import com.example.kodeemployees.domain.repository.UsersRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(userId: String): User? {
        return usersRepository.getLocalUser(userId)
    }
}