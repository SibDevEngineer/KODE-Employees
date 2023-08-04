package com.example.kodeemployees.presentation.screens.user_detail.mapper

import android.content.Context
import com.example.kodeemployees.R
import com.example.kodeemployees.domain.models.User
import com.example.kodeemployees.presentation.screens.user_detail.models.UserDetailUi
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class UserToUserDetailMapper @Inject constructor(private val context: Context) {
    fun map(user: User): UserDetailUi {
        val calNow = Calendar.getInstance()
        val calUser = Calendar.getInstance().apply { time = user.birthdate ?: Date() }
        val intAge = calNow.get(Calendar.YEAR) - calUser.get(Calendar.YEAR)
        val userAge = context.resources.getQuantityString(
            R.plurals.age, intAge, intAge
        )

        val formatter = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())

        return UserDetailUi(
            id = user.id,
            avatarUrl = user.avatarUrl,
            userName = user.userName ?: context.getString(
                R.string.user_detail_screen_empty_name
            ),
            userTag = user.userTag ?: "",
            profession = user.profession ?: context.getString(
                R.string.user_detail_screen_empty_profession
            ),
            birthdate = user.birthdate?.let { formatter.format(it) },
            age = userAge,
            phone = user.phone ?: context.getString(R.string.user_detail_screen_empty_phone)
        )
    }
}