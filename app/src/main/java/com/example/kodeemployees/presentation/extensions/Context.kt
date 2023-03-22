package com.example.kodeemployees.presentation.extensions

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat

fun Context.hasPermissions(vararg permissions: String) = permissions.all { permission ->
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.showToast(message: String?) {
    message?.let { Toast.makeText(this, message, Toast.LENGTH_LONG).show() }
}