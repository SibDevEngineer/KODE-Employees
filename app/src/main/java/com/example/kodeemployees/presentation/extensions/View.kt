package com.example.kodeemployees.presentation.extensions

import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.CheckResult
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onStart

/** visibility = View.VISIBLE */
fun View.show() {
    visibility = View.VISIBLE
}

/** visibility = View.GONE */
fun View.gone() {
    visibility = View.GONE
}

/** Управление видимостью View по определенному условию */
inline fun View.showIf(condition: View.() -> Boolean) {
    if (condition()) {
        show()
    } else {
        gone()
    }
}

/** Задание оттенка компоненту View*/
fun ImageView.setTint(@ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(
        this,
        ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
    )
}

/** Функция реализует задержку при вводе символов в EditText перед выполнением какого либо действия */
@OptIn(FlowPreview::class)
@CheckResult
fun EditText.textChangesWithDebounce(debounceMillis: Long): Flow<CharSequence?> {
    return callbackFlow {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySend(s)
            }
        }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }.debounce(debounceMillis)
}