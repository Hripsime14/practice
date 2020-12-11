package com.cypress.myapplication.fragments.login

class LoginDataRepo(private val loginLocalDataSource: LoginLocalDataSource) {

    fun setLoginFinished(isLoginFinished: Boolean) = loginLocalDataSource.setLoginFinished(isLoginFinished)

}