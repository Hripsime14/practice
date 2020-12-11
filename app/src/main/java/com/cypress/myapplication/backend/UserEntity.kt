package com.cypress.myapplication.backend

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cypress.myapplication.constants.USER_DB
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = USER_DB)
data class UserEntity(
    @PrimaryKey (autoGenerate = false)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("mail")
    val mail: String,
    @field:SerializedName("username")
    val username: String) : Parcelable