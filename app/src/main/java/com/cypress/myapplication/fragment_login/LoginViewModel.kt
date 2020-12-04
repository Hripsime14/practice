package com.cypress.myapplication.fragment_login

import androidx.lifecycle.ViewModel

class LoginViewModel(private val loginDataRepo: LoginDataRepo): ViewModel() {

      fun setLoginFinished(isLoginFinished: Boolean) = loginDataRepo.setLoginFinished(isLoginFinished)
}