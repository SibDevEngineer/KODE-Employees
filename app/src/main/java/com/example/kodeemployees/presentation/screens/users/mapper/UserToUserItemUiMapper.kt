package com.example.kodeemployees.presentation.screens.users.mapper

import com.example.kodeemployees.domain.models.User
import com.example.kodeemployees.presentation.screens.users.models.UserItemUi
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class UserToUserItemUiMapper @Inject constructor() {
    fun map(
        usersList: List<User>,
        isShowBirthdate: Boolean,
        isFindUser: Boolean
    ): List<UserItemUi> {
        val calNow = Calendar.getInstance()
        val nextYear = calNow.get(Calendar.YEAR) + 1

        when {
            usersList.isEmpty() && isFindUser -> { //если пустой список и мы ищем юзера
                return listOf(UserItemUi.UserNotFoundItem)
            }

            usersList.isEmpty() && !isFindUser -> { //если пустой список и мы не ищем юзера
                return listOf(UserItemUi.EmptyListItem)
            }
        }

        //в остальных случаях
        val userItemUiList: MutableList<UserItemUi> =
            usersList.map { user ->
                val formatter = SimpleDateFormat("d MMM", Locale.getDefault())
                val birthdateUi = user.birthdate?.let { formatter.format(it) }

                UserItemUi.User(
                    id = user.id,
                    avatarUrl = user.avatarUrl,
                    userName = user.userName,
                    userTag = user.userTag,
                    profession = user.profession,
                    isShowBirthdate = isShowBirthdate,
                    birthdateUI = birthdateUi,
                )
            }.toMutableList()

        //добавляем header в список
        if (isShowBirthdate) {
            val header = UserItemUi.Header(nextYear.toString())

            val indexBirthdateNextYear = usersList.indexOfFirst { user ->
                val cal = Calendar.getInstance().apply {
                    time = user.nextBirthdate ?: Date()
                }
                cal.get(Calendar.YEAR) > calNow.get(Calendar.YEAR)
            }

            if (indexBirthdateNextYear >= 0) {
                userItemUiList.add(indexBirthdateNextYear, header)
            }
        }
        return userItemUiList.toList()
    }
}