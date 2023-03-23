package com.example.kodeemployees.data.dto

import com.example.kodeemployees.data.extensions.toDate
import com.example.kodeemployees.presentation.models.User
import java.util.*

/** Этот адрес - базовый для генерации случайных аватарок пользователей, чтобы
 * не показывать только заглушку вместо аватарок в списке пользователей.
 * Причина: бэкэнд выдает не рабочие ссылки на аватарки пользователей.
 * Для формирования итоговой ссылки на аватарку нужно добавить к базовой ссылке хотя бы 1 символ.
 * Ограничений на язык ввода, цифры/символы нет.
 */
private const val MOCKED_AVATAR_URL = "https://robohash.org/"

fun UserDto.toUser(): User {
    val birthdate = birthday?.toDate()

    val calNow = Calendar.getInstance()
    val calBirthDate = Calendar.getInstance().apply { time = birthdate ?: Date() }

    val calNextBirthdate = Calendar.getInstance().apply {
        set(Calendar.DATE, calBirthDate.get(Calendar.DATE))
        set(Calendar.MONTH, calBirthDate.get(Calendar.MONTH))
        set(Calendar.YEAR, calNow.get(Calendar.YEAR)) // текущий год по умолчанию
    }

    // Если текуший день в году больше чем день в году даты рождения,
    // то следующая дата рождения будет в следующем году
    if (calNow.get(Calendar.DAY_OF_YEAR) > calBirthDate.get(Calendar.DAY_OF_YEAR)) {
        calNextBirthdate.add(Calendar.YEAR, 1)
    }

    val nextBirthdate = calNextBirthdate.time

    return User(
        id = id,
        avatarUrl = "$MOCKED_AVATAR_URL$id",
        userName = "$firstName $lastName",
        userTag = userTag?.lowercase(),
        department = department,
        profession = position,
        birthdate = birthdate,
        nextBirthdate = nextBirthdate,
        phone = phone
    )
}