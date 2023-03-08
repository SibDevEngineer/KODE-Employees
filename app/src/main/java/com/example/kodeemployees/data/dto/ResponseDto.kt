package com.example.kodeemployees.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseDto(
    @Json(name = "items") val usersList: List<UserDto>
)

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "id") val id: String,
    @Json(name = "avatarUrl") val avatarUrl: String?,
    @Json(name = "firstName") val firstName: String?,
    @Json(name = "lastName") val lastName: String?,
    @Json(name = "userTag") val userTag: String?,
    @Json(name = "department") val department: String?,
    @Json(name = "position") val position: String?,
    @Json(name = "birthday") val birthday: String?,
    @Json(name = "phone") val phone: String?
)

