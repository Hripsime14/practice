package com.cypress.myapplication.fragment_login

import android.content.SharedPreferences
import com.cypress.myapplication.constants.LOGIN_KEY

class LoginLocalDataSource(private var sharedPreference: SharedPreferences) {

    fun setLoginFinished(isLoginFinished: Boolean) {
        val editor = sharedPreference?.edit()
        editor?.putBoolean(LOGIN_KEY, isLoginFinished)
        editor?.apply()
    }
}