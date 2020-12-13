package com.cypress.myapplication.fragments.contacts

import android.app.Activity
import androidx.lifecycle.ViewModel

class ContactViewModel(private val contactDataRepo: ContactDataRepo): ViewModel() {

    fun getContacts(activity: Activity)  = contactDataRepo.getContacts(activity)

    fun getLiveData()  = contactDataRepo.getLiveData()
}