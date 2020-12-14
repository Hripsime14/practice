package com.cypress.myapplication.manager

import android.app.Activity
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cypress.myapplication.modeldatas.model.ContactItem

class ContactManager {
    private val contacts = mutableListOf<ContactItem>()
    private val _liveData = MutableLiveData<List<ContactItem>>()
    fun getLiveData(): LiveData<List<ContactItem>> = _liveData

    fun readContacts(activity: Activity) {
        contacts.clear()

        val phoneCols = listOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        ).toTypedArray()

        val emailCols = listOf(
            ContactsContract.CommonDataKinds.Email.ADDRESS,
            ContactsContract.CommonDataKinds.Email.CONTACT_ID
        ).toTypedArray() //TODO: check what is typed array


        lateinit var contactItem: ContactItem
        val phoneResult = activity.contentResolver?.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            phoneCols, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

        val emailResult = activity.contentResolver?.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            emailCols, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

        if (phoneResult != null && phoneResult.count > 0 && emailResult != null && emailResult.count > 0) {
            phoneResult.moveToFirst()
            emailResult.moveToFirst()
            do {
                if (phoneResult.getString(3) == emailResult.getString(1)) {
                    contactItem = ContactItem(phoneResult.getString(0),
                        phoneResult.getString(1), "", emailResult.getString(0))

                    if (phoneResult.getString(2) != null) contactItem.photo = phoneResult.getString(2)
                    contacts.add(contactItem)
                        if(!emailResult.moveToNext()) break
                }
            }
            while (phoneResult.moveToNext())
        }
        phoneResult?.close()
        emailResult?.close()
        _liveData.value = contacts
    }
}