package com.cypress.myapplication.util

import android.util.Patterns


fun isEmailValid(email: String): Boolean{

    if (email.isEmpty()) return false
    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) return true
    return false
}