package com.cypress.myapplication.activities.main

class MainDataRepo(private val localDataSource: MainLocalDataSource) {

    fun getIntroFinished(): Boolean = localDataSource.getIntroFinished()

    fun getLoginFinished(): Boolean = localDataSource.getLoginFinished()
}