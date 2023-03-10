package com.example.kodeemployees.presentation.screens.users

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kodeemployees.domain.MainRepository
import com.example.kodeemployees.presentation.UIState
import com.example.kodeemployees.presentation.screens.users.adapter.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class UsersViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _usersStateFlow = MutableStateFlow<List<User>>(emptyList())
    val usersStateFlow = _usersStateFlow.asStateFlow()

    private val _uiStateFlow = MutableStateFlow<UIState>(UIState.Default)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    init {
        _uiStateFlow.value = UIState.Loading
        getAllUsers()
    }

    fun refreshUsersList() {
        _uiStateFlow.value = UIState.Refreshing
        getAllUsers()
    }

    private fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { mainRepository.getMockUsers() }.fold(
                onSuccess = {
                    _uiStateFlow.value = UIState.Default
                    _usersStateFlow.value = it
                },
                onFailure = {
                    _uiStateFlow.value = UIState.Error(it.message)
                    Log.d("UsersViewModel", "${it.message}")
                }
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
}