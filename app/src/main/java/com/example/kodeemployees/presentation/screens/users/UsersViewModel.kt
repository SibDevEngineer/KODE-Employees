package com.example.kodeemployees.presentation.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kodeemployees.domain.MainRepository
import com.example.kodeemployees.presentation.screens.users.adapter.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class UsersViewModel @Inject constructor(
    private val mainRepositoryProvider: Provider<MainRepository>
) : ViewModel() {

    private val mainRepository by lazy { mainRepositoryProvider.get() }

    private val _usersStateFlow = MutableStateFlow<List<User>>(emptyList())
    val usersStateFlow = _usersStateFlow.asStateFlow()

    fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { mainRepository.getMockUsers() }.fold(
                onSuccess = { _usersStateFlow.value = it },
                onFailure = { }
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val viewModerProvider: Provider<UsersViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == UsersViewModel::class.java)
            return viewModerProvider.get() as T
        }
    }

//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            require(modelClass == UsersViewModel::class.java)
//            return viewModerProvider.get() as T
//        }
//    }

}