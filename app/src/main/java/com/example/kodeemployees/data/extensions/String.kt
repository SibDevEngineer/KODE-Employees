package com.example.kodeemployees.data.extensions

import java.text.SimpleDateFormat
import java.util.*

/** Функция преобразует стровое значения поля дня рождения пользователя */
fun String.toDate(): Date? {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    return try {
        sdf.parse(this)
    } catch (e: Exception) {
        return null
    }
}