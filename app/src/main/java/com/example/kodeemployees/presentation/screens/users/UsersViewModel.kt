package com.example.kodeemployees.presentation.screens.users

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kodeemployees.data.models.DataSourceType
import com.example.kodeemployees.data.models.RequestParams
import com.example.kodeemployees.data.models.SortUsersType
import com.example.kodeemployees.domain.MainRepository
import com.example.kodeemployees.presentation.UIState
import com.example.kodeemployees.presentation.models.DepartmentType
import com.example.kodeemployees.presentation.models.User
import com.example.kodeemployees.presentation.screens.users.adapter.UserItemUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class UsersViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _usersStateFlow = MutableStateFlow<List<UserItemUI>>(emptyList())
    val usersStateFlow = _usersStateFlow.asStateFlow()

    private val _uiStateFlow = MutableStateFlow<UIState>(UIState.Default)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private val requestParams = RequestParams()

    init {
        _uiStateFlow.value = UIState.Loading
        getUsers()

    }

    fun refreshUsersList() {
        _uiStateFlow.value = UIState.Refreshing
        requestParams.sourceType = DataSourceType.SERVER
        getUsers()
    }

    fun onDepartmentSelected(departmentType: DepartmentType) {
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

    fun findUser(title: String) {
        with(requestParams) {
            if (searchText != title) {
                searchText = title
                sourceType = DataSourceType.CACHE

                viewModelScope.launch() {
                    kotlin.runCatching { mainRepository.getUsers(requestParams) }.fold(
                        onSuccess = { usersList ->
                            _uiStateFlow.value =
                                if (usersList.isEmpty()) UIState.UserNotFound else UIState.Default

                            _usersStateFlow.value = mapToUsersUi(usersList)
                        },
                        onFailure = {
                            _uiStateFlow.value = UIState.Error(it.message)
                            Log.d("UsersViewModel", "${it.message}")
                        }
                    )
                }
            }

        }
    }

    /** Возвращает текущий вид сортировки пользователей */
    fun getCurrentSortType(): SortUsersType = requestParams.sortedBy

    private fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching { mainRepository.getUsers(requestParams) }.fold(
                onSuccess = { usersList ->
                    withContext(Dispatchers.Main) {
                        _uiStateFlow.value =
                            if (usersList.isEmpty()) UIState.EmptyList else UIState.Default

                        _usersStateFlow.value = mapToUsersUi(usersList)
                    }
                },
                onFailure = {
                    _uiStateFlow.value = UIState.Error(it.message)
                    Log.d("UsersViewModel", "${it.message}")
                }
            )
        }
    }

    /** Функция возвращает список пользователей, преобразуя модель из data слоя
     * в модель для presentation слоя. При сортировке списка по дате в список добавляется header
     * с инфо о днях рождения в следующем году */
    private fun mapToUsersUi(list: List<User>): List<UserItemUI> {
        val isShowBirthdate = requestParams.sortedBy == SortUsersType.BIRTHDATE
        val calNow = Calendar.getInstance()
        val nextYear = calNow.get(Calendar.YEAR) + 1

        val newList = mutableListOf<UserItemUI>()

        newList.addAll(list.map { user ->
            val formatter = SimpleDateFormat("d MMM", Locale.getDefault())
            val birthdateUI = user.birthdate?.let { formatter.format(it) }

            UserItemUI.UserUI(
                user = user,
                isShowBirthdate = isShowBirthdate,
                birthdateUI = birthdateUI
            )
        })

        //добавляем header в список
        if (isShowBirthdate) {
            val headerUI = UserItemUI.HeaderUI(nextYear.toString())

            val indexBirthdateNextYear = list.indexOfFirst { user ->
                val cal = Calendar.getInstance().apply { time = user.nextBirthdate ?: Date() }
                cal.get(Calendar.YEAR) > calNow.get(Calendar.YEAR)
            }

            if (indexBirthdateNextYear >= 0) {
                newList.add(indexBirthdateNextYear, headerUI)
            }
        }
        return newList.toList()
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