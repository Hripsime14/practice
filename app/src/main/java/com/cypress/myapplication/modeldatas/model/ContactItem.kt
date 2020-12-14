package com.cypress.myapplication.modeldatas.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContactItem(
    var fullName: String,
    var number: String,
    var photo: String,
    var email: String
): Parcelable