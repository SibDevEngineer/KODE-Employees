package com.example.kodeemployees.di

import com.example.kodeemployees.core.coroutines.AppDispatchers
import com.example.kodeemployees.core.coroutines.AppDispatchersImpl
import com.example.kodeemployees.data.local_storage.UsersLocalStorage
import com.example.kodeemployees.data.local_storage.UsersLocalStorageImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataModule {

    @Singleton
    @Binds
    fun bindUsersStorageImpl_to_UsersStorage(
        usersLocalStorageImpl: UsersLocalStorageImpl
    ): UsersLocalStorage

    @Singleton
    @Binds
    fun bindAppDispatcherImpl_to_AppDispatcher(
        appDispatchersImpl: AppDispatchersImpl
    ): AppDispatchers
}