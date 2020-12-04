package com.cypress.myapplication.main

class MainDataRepo(private val localDataSource: MainLocalDataSource) {

    fun getIntroFinished(): Boolean = localDataSource.getIntroFinished()

    fun getLoginFinished(): Boolean = localDataSource.getLoginFinished()
}