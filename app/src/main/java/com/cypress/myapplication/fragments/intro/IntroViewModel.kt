package com.cypress.myapplication.fragments.intro

import androidx.lifecycle.ViewModel

class IntroViewModel(private val introDataRepo: IntroDataRepo): ViewModel() {

    fun setIntroFinished() = introDataRepo.setIntroFinished()

}