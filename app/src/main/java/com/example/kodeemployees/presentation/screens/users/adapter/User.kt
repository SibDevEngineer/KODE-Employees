package com.example.kodeemployees.presentation.screens.users.adapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val avatarUrl: String?,
    val firstName: String?,
    val lastName: String?,
    val userTag: String?,
    val department: String?,
    val position: String?,
    val birthday: String?,
    val phone: String?
) : Parcelable