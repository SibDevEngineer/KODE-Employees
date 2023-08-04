package com.example.kodeemployees.domain.models

import java.util.Date

/** Класс, описывающий работника/пользователя */
data class User(
    val id: String,
    val avatarUrl: String?,
    val userName: String?,
    val userTag: String?,
    val department: String?,
    val profession: String?,
    val birthdate: Date?,
    val nextBirthdate:Date?,
    val phone: String?
)