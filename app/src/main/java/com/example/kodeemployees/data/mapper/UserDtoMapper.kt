package com.example.kodeemployees.data.mapper

import com.example.kodeemployees.core.extensions.toDate
import com.example.kodeemployees.data.dto.UserDto
import com.example.kodeemployees.domain.models.User
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

/** Этот адрес - базовый для генерации случайных аватарок пользователей, чтобы
 * не показывать только заглушку вместо аватарок в списке пользователей.
 * Причина: бэкэнд выдает не рабочие ссылки на аватарки пользователей.
 * Для формирования итоговой ссылки на аватарку нужно добавить к базовой ссылке хотя бы 1 символ.
 * Ограничений на язык ввода, цифры/символы нет.
 */
private const val MOCKED_AVATAR_URL = "https://robohash.org/"

class UserDtoMapper @Inject constructor() {
    fun mapToUser(userDto: UserDto): User {
        val birthdate = userDto.birthday?.toDate()

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
            id = userDto.id,
            avatarUrl = "${MOCKED_AVATAR_URL}${userDto.id}",
            userName = "${userDto.firstName} ${userDto.lastName}",
            userTag = userDto.userTag?.lowercase(),
            department = userDto.department,
            profession = userDto.position,
            birthdate = birthdate,
            nextBirthdate = nextBirthdate,
            phone = userDto.phone
        )
    }
}

