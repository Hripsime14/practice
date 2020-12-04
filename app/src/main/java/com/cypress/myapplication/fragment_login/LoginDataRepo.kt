package com.cypress.myapplication.fragment_login

class LoginDataRepo(private val loginLocalDataSource: LoginLocalDataSource) {

    fun setLoginFinished(isLoginFinished: Boolean) = loginLocalDataSource.setLoginFinished(isLoginFinished)

}