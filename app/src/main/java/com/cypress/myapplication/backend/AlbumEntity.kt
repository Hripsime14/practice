package com.cypress.myapplication.backend

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cypress.myapplication.constants.ALBUM_DB
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = ALBUM_DB)
data class AlbumEntity(
    @PrimaryKey(autoGenerate = false)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String) : Parcelable