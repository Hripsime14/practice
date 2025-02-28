package com.cypress.myapplication.fragments.login

import androidx.lifecycle.ViewModel

class LoginViewModel(private val loginDataRepo: LoginDataRepo): ViewModel() {

      fun setLoginFinished() = loginDataRepo.setLoginFinished()
}