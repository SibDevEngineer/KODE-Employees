package com.example.kodeemployees.di

import com.example.kodeemployees.presentation.screens.users.UsersFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApiModule::class, DomainModule::class]
)
interface AppComponent {

    fun inject(fragment:UsersFragment)
}