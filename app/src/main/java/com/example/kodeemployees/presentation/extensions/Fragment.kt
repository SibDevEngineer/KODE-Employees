package com.example.kodeemployees.presentation.extensions

import android.os.Build
import androidx.fragment.app.Fragment

/** Функция для получения данных типа Parcelable из аргументов фрагмента  */
inline fun <reified T> Fragment.getParcelableData(key: String, default: T? = null): Lazy<T?> =
    lazy {
        return@lazy if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(key, T::class.java) ?: default
        } else {
            @Suppress("DEPRECATION") arguments?.getParcelable(key) as? T
        }
    }


