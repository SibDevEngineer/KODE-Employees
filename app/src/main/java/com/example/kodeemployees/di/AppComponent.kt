package com.example.kodeemployees.di

import android.content.Context
import com.example.kodeemployees.presentation.screens.user_detail.UserDetailFragment
import com.example.kodeemployees.presentation.screens.users.UsersFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ ApiModule::class, DomainModule::class, DataModule::class]
)
interface AppComponent {
    fun inject(fragment: UsersFragment)
    fun inject(fragment: UserDetailFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindContext(context: Context): Builder
        fun build(): AppComponent
    }
}