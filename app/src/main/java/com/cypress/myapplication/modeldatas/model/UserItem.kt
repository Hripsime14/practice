package com.cypress.myapplication.modeldatas.model

import com.google.gson.annotations.SerializedName

data class UserItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("username")
    val username: String,
)