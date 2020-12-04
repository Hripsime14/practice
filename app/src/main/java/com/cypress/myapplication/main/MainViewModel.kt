package com.cypress.myapplication.main

import androidx.lifecycle.ViewModel

class MainViewModel(private val mainDataRepo: MainDataRepo): ViewModel() {

    fun getIntroFinished(): Boolean = mainDataRepo.getIntroFinished()

    fun getLoginFinished(): Boolean = mainDataRepo.getLoginFinished()
}