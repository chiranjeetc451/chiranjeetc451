package com.mindyug.app.data.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.provider.Settings.System.putString
import javax.inject.Singleton

@Singleton
class SharedPrefs(context: Context) {
    private val prefs = context.getSharedPreferences(LOGIN_STATE, MODE_PRIVATE)
    private val isUserLoggedIn = prefs.getBoolean(IS_USER_LOGGED_IN, false)

    fun toggleLogin(){
        prefs.edit().putBoolean(IS_USER_LOGGED_IN, !isUserLoggedIn).apply()
    }

    companion object {
        const val LOGIN_STATE = "user_login_state"
        const val IS_USER_LOGGED_IN = "is_user_logged_in"
    }
}