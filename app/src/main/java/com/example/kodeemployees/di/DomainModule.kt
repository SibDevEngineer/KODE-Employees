package com.example.kodeemployees.di

import com.example.kodeemployees.data.repository.MainRepositoryImpl
import com.example.kodeemployees.domain.MainRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @Binds
    fun bindMainRepository(
        mainRepositoryImpl: MainRepositoryImpl
    ): MainRepository
}