package com.example.kodeemployees.presentation.screens.users

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kodeemployees.data.models.DataSourceType
import com.example.kodeemployees.data.models.RequestParams
import com.example.kodeemployees.data.models.SortUsersType
import com.example.kodeemployees.domain.UsersUseCase
import com.example.kodeemployees.presentation.UIState
import com.example.kodeemployees.presentation.models.DepartmentType
import com.example.kodeemployees.presentation.models.User
import com.example.kodeemployees.presentation.screens.users.adapter.UserItemUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class UsersViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase
) : ViewModel() {

    private val _usersStateFlow = MutableStateFlow<List<UserItemUI>>(emptyList())
    val usersStateFlow = _usersStateFlow.asStateFlow()

    private val _uiStateFlow = MutableStateFlow<UIState>(UIState.Default)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private val requestParams = RequestParams()

    init {
        _uiStateFlow.value = UIState.Loading
        mockSkeletons()
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
            kotlin.runCatching { usersUseCase.getUsers(requestParams) }.fold(
                onSuccess = { usersList ->
                    _uiStateFlow.value = if (usersList.isEmpty()) getUIStateEmpty() else UIState.Default
                    _usersStateFlow.value = mapToUsersUi(usersList)
                },
                onFailure = {
                    _uiStateFlow.value = UIState.Error(it.message)
                    Log.d("UsersViewModel", "${it.message}")
                }
            )
        }
    }

    /** Добавление скелетонов в момент загрузки данных */
    private fun mockSkeletons() {
        val countSkeletons = 15
        val skeletonsList = mutableListOf<UserItemUI>()
        for (i in 0 until countSkeletons) {
            skeletonsList.add(UserItemUI.Skeleton)
        }
        _usersStateFlow.value = skeletonsList
    }

    private fun getUIStateEmpty(): UIState {
        return if (requestParams.searchText.isEmpty()) UIState.EmptyList
        else UIState.UserNotFound
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