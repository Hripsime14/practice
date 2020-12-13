package com.cypress.myapplication.fragments.contacts

import android.app.Activity

class ContactDataRepo(private val localDataSource: ContactLocalDataSource) {

    fun getContacts(activity: Activity)  = localDataSource.getContacts(activity)

    fun getLiveData()  = localDataSource.getLiveData()
}