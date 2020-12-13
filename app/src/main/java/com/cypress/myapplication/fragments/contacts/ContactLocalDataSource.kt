package com.cypress.myapplication.fragments.contacts

import android.app.Activity
import com.cypress.myapplication.manager.ContactManager

class ContactLocalDataSource(private val contactManager: ContactManager) {

    fun getContacts(activity: Activity)  = contactManager.readContacts(activity)

    fun getLiveData()  = contactManager.getLiveData()
}