package com.cypress.myapplication.backend

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cypress.myapplication.constants.PHOTO_DB
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = PHOTO_DB)
data class PhotoEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val albumId: Int,
    val name: String,
    val url: String,
    val thumbnailUrl: String
    ) : Parcelable
