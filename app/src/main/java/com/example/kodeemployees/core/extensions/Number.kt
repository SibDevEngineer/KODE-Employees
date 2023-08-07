package com.example.kodeemployees.core.extensions

import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.Px

@Px
fun <T : Number> T.dpToPx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
}
