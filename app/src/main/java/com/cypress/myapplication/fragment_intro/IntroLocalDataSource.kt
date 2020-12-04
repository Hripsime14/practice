package com.cypress.myapplication.fragment_intro

import android.content.SharedPreferences
import com.cypress.myapplication.constants.INTRO_KEY

class IntroLocalDataSource(private var sharedPreference: SharedPreferences) {

    fun setIntroFinished(isIntroFinished: Boolean) {
        val editor = sharedPreference?.edit()
        editor?.putBoolean(INTRO_KEY, isIntroFinished)
        editor?.apply()
    }
}