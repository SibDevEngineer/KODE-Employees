package com.example.kodeemployees.presentation.screens.users

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kodeemployees.data.models.DataSourceType
import com.example.kodeemployees.data.models.RequestParams
import com.example.kodeemployees.data.models.SortUsersType
import com.example.kodeemployees.domain.models.Department
import com.example.kodeemployees.domain.usecases.GetDepartmentsUseCase
import com.example.kodeemployees.domain.usecases.GetUsersUseCase
import com.example.kodeemployees.presentation.screens.users.mapper.UserToUserItemUiMapper
import com.example.kodeemployees.presentation.screens.users.models.UserItemUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val getDepartmentsUseCase: GetDepartmentsUseCase,
    private val userToUserItemUiMapper: UserToUserItemUiMapper
) : ViewModel() {

    private val _departmentsStateFlow = MutableStateFlow<List<Department>>(emptyList())
    val departmentsStateFlow = _departmentsStateFlow.asStateFlow()

    private val _screenStateFlow = MutableStateFlow<ScreenState<List<UserItemUi>>>(ScreenState.Data(emptyList()))
    val screenStateFlow = _screenStateFlow.asStateFlow()

    private val requestParams = RequestParams()

    private val actualUiItems: List<UserItemUi>
        get() = (_screenStateFlow.value as? ScreenState.Data)?.data ?: emptyList()

    init {
        _departmentsStateFlow.value = getDepartmentsUseCase()
        mockSkeletons()
        getUsers()
    }

    fun refreshUsersList() {
        _screenStateFlow.value = ScreenState.Data(
            data = actualUiItems,
            updating = true
        )
        requestParams.sourceType = DataSourceType.SERVER
        getUsers()
    }

    fun onDepartmentSelected(departmentType: Department) {
        with(requestParams) {
            department = departmentType
            sourceType = DataSourceType.CACHE
        }
        getUsers()
    }

    fun changeSortingType(sortType: SortUsersType) {
        with(requestParams) {
            sortedBy = sortType
            sourceType = DataSourceType.CACHE
        }
        getUsers()
    }

    fun onSearchTextChanged(text: String) {
        if (text == requestParams.searchText) return

        with(requestParams) {
            searchText = text
            sourceType = DataSourceType.CACHE
        }
        getUsers()
    }

    /** Возвращает текущий вид сортировки пользователей */
    fun getCurrentSortType(): SortUsersType = requestParams.sortedBy

    private fun getUsers() {
        viewModelScope.launch {
            kotlin.runCatching { getUsersUseCase(requestParams) }
                .fold(
                    onSuccess = { usersList ->
                        val isShowBirthdate = requestParams.sortedBy == SortUsersType.BIRTHDATE
                        val isFindUser = requestParams.searchText.isNotEmpty()

                        _screenStateFlow.value = ScreenState.Data(
                            userToUserItemUiMapper.map(
                                usersList,
                                isShowBirthdate,
                                isFindUser
                            )
                        )
                    },
                    onFailure = {
                        _screenStateFlow.value = ScreenState.Error(it.message)
                        Log.d("UsersViewModel", "${it.message}")
                    }
                )
        }
    }

    /** Добавление скелетонов в момент загрузки данных */
    private fun mockSkeletons() {
        val countSkeletons = 15
        val skeletonsList = mutableListOf<UserItemUi>()
        for (i in 0 until countSkeletons) {
            skeletonsList.add(UserItemUi.Skeleton)
        }
        _screenStateFlow.value = ScreenState.Data(skeletonsList)
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