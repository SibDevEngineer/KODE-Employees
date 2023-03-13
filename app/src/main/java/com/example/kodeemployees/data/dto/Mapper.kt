package com.example.kodeemployees.data.dto

import com.example.kodeemployees.presentation.models.User

/** Этот адрес - базовый для генерации случайных аватарок пользователей, чтобы
 * не показывать только заглушку вместо аватарок в списке пользователей.
 * Причина: бэкэнд выдает не рабочие ссылки на аватарки пользователей.
 * Для формирования итоговой ссылки на аватарку нужно добавить к базовой ссылке хотя бы 1 символ.
 * Ограничений на язык ввода, цифры/символы нет.
 */
private const val MOCKED_AVATAR_URL = "https://robohash.org/"

fun UserDto.toUser(): User {
    return User(
        id = id,
        avatarUrl = "$MOCKED_AVATAR_URL$id",
        userName = "$firstName $lastName",
        userTag = userTag?.lowercase(),
        department = department,
        profession = position,
        birthday = birthday,
        phone = phone
    )
}