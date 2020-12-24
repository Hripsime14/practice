package com.cypress.myapplication.activities.main

import android.content.SharedPreferences
import com.cypress.myapplication.constants.INTRO_KEY
import com.cypress.myapplication.constants.LOGIN_KEY

class MainLocalDataSource(private var sharedPreference: SharedPreferences) {

    fun getIntroFinished(): Boolean =
        sharedPreference.getBoolean(INTRO_KEY, false)

    fun getLoginFinished(): Boolean =
        sharedPreference.getBoolean(LOGIN_KEY, false)
}