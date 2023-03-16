package com.example.kodeemployees.presentation.extensions

import android.os.Build
import android.os.Bundle

inline fun <reified T> Bundle.getParcelableData(key: String, default: T? = null): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, T::class.java) ?: default
    } else {
        getParcelable(key) as? T
    }
}