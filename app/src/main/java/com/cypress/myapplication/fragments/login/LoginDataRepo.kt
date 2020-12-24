package com.cypress.myapplication.fragments.login

class LoginDataRepo(private val loginLocalDataSource: LoginLocalDataSource) {

    fun setLoginFinished() = loginLocalDataSource.setLoginFinished()

}