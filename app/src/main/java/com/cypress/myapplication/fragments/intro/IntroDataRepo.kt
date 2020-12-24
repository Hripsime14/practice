package com.cypress.myapplication.fragments.intro

class IntroDataRepo(private val introLocalDataSource: IntroLocalDataSource) {

    fun setIntroFinished() = introLocalDataSource.setIntroFinished()
}