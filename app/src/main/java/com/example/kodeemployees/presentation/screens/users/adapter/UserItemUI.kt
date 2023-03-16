package com.example.kodeemployees.presentation.screens.users.adapter

import com.example.kodeemployees.presentation.models.User
import java.text.SimpleDateFormat
import java.util.*

sealed class UserItemUI {
    data class UserUI(
        val user: User,
        val isShowBirthdate: Boolean,
        val birthdateUI: String?
    ) : UserItemUI()

    data class HeaderUI(val title: String) : UserItemUI()

    companion object {
        fun User.toUserUI(isShowBirthdate: Boolean): UserUI {
            val date = if (isShowBirthdate) {
                val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())//d MMM
                this.birthdate?.let { formatter.format(it) }
            } else null

            return UserUI(
                user = this,
                isShowBirthdate = isShowBirthdate,
                birthdateUI = date
            )
        }
    }
}
