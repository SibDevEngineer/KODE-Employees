package com.example.kodeemployees.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class SortUsersType : Parcelable {
    ALPHABET, BIRTHDATE
}