package com.example.kodeemployees.presentation.extensions

import android.os.Build
import androidx.fragment.app.Fragment
import java.io.Serializable

/** Функция для получения данных типа Parcelable из аргументов фрагмента  */
inline fun <reified T> Fragment.getParcelableData(key: String, default: T? = null): Lazy<T?> =
    lazy {
        return@lazy if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(key, T::class.java) ?: default
        } else {
            @Suppress("DEPRECATION") arguments?.getParcelable(key) as? T
        }
    }

/** Функция для получения данных типа Serializable из аргументов фрагмента  */
inline fun <reified T : Serializable> Fragment.getSerializableData(
    key: String,
    default: T? = null
): Lazy<T?> =
    lazy {
        return@lazy if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(key, T::class.java) ?: default
        } else {
            @Suppress("DEPRECATION") arguments?.getSerializable(key) as? T
        }
    }


