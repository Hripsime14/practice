package com.cypress.myapplication.fragment_intro

class IntroDataRepo(val introLocalDataSource: IntroLocalDataSource) {

    fun setIntroFinished(isIntroFinished: Boolean) = introLocalDataSource.setIntroFinished(isIntroFinished)
}