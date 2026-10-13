package com.example.littlelemonfinalnc

import android.content.Context
import android.content.SharedPreferences
import android.util.Patterns


fun saveUserData(context: Context, firstName: String, lastName: String, email: String) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("LittleLemonPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    editor.putString("firstName", firstName)
    editor.putString("lastName", lastName)
    editor.putString("email", email)
    editor.putBoolean("isLoggedIn", true)

    editor.apply() // .apply() saves the data asynchronously
}


fun isValidEmail(email: String): Boolean {
    // This uses Android's official, pre-built email formatting rules
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun clearUserData(context: Context) {
    val sharedPreferences = context.getSharedPreferences("LittleLemonPrefs", Context.MODE_PRIVATE)
    // .clear() removes all saved keys, and .apply() saves the changes
    sharedPreferences.edit().clear().apply()
}