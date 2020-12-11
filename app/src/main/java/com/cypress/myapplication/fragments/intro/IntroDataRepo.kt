package com.cypress.myapplication.fragments.intro

class IntroDataRepo(val introLocalDataSource: IntroLocalDataSource) {

    fun setIntroFinished(isIntroFinished: Boolean) = introLocalDataSource.setIntroFinished(isIntroFinished)
}