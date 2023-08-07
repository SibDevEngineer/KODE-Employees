package com.example.kodeemployees.di

import com.example.kodeemployees.data.repository.UsersRepositoryImpl
import com.example.kodeemployees.domain.repository.UsersRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindUsersRepositoryImpl_to_UsersRepository(
        usersRepositoryImpl: UsersRepositoryImpl
    ): UsersRepository


}