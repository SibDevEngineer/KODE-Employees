package com.example.kodeemployees.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/** Класс, описывающий работника/пользователя */
@Parcelize
data class User(
    val id: String,
    val avatarUrl: String?,
    val userName: String?,
    val userTag: String?,
    val department: String?,
    val profession: String?,
    val birthday: String?,
    val phone: String?
) : Parcelable