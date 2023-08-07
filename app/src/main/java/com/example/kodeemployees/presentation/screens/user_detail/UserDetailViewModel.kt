package com.example.kodeemployees.presentation.screens.user_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kodeemployees.domain.usecases.GetUserDetailUseCase
import com.example.kodeemployees.presentation.screens.user_detail.mapper.UserToUserDetailMapper
import com.example.kodeemployees.presentation.screens.user_detail.models.UserDetailUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val userId: String,
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val userToUserDetailMapper: UserToUserDetailMapper,
) : ViewModel() {

    private val _userDetailStateFlow = MutableStateFlow<UserDetailUi?>(null)
    val userDetailStateFlow = _userDetailStateFlow.asStateFlow().filterNotNull()

    init {
        getUserDetail(userId)
    }

    fun getPhoneNumber(): String? {
        return _userDetailStateFlow.value?.phone
    }

    private fun getUserDetail(userId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                getUserDetailUseCase(userId)
            }.fold(
                onSuccess = { user ->
                    val userDetail = user?.let { userToUserDetailMapper.map(it) }
                    _userDetailStateFlow.value = userDetail
                },
                onFailure = {
                    Log.d("UserDetailViewModel", "${it.message}")
                }
            )
        }
    }
}

@Suppress("UNCHECKED_CAST")
class UserDetailViewModelFactory @AssistedInject constructor(
    @Assisted("userId") private val userId: String,
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val userToUserDetailMapper: UserToUserDetailMapper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == UserDetailViewModel::class.java)
        return UserDetailViewModel(
            userId,
            getUserDetailUseCase,
            userToUserDetailMapper
        ) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("userId") userId: String): UserDetailViewModelFactory
    }
}