package com.cypress.myapplication.fragments.login

import android.content.SharedPreferences
import com.cypress.myapplication.constants.LOGIN_KEY

class LoginLocalDataSource(private var sharedPreference: SharedPreferences) {

    fun setLoginFinished() {
        val editor = sharedPreference.edit()
        editor?.putBoolean(LOGIN_KEY, true)
        editor?.apply()
    }
}