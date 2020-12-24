package com.cypress.myapplication.fragments.intro

import android.content.SharedPreferences
import com.cypress.myapplication.constants.INTRO_KEY

class IntroLocalDataSource(private var sharedPreference: SharedPreferences) {

    fun setIntroFinished() {
        val editor = sharedPreference.edit()
        editor?.putBoolean(INTRO_KEY, true)
        editor?.apply()
    }
}