package com.cypress.myapplication.modeldatas.model

import android.os.Parcelable
import com.cypress.myapplication.fragments.media.Mode
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MediaItem(
    var id: String,
    var title: String,
    var uri: String,
    var isMusic: Boolean,
    var mode: Mode = Mode.PAUSE,
    var isIconVisibile: Boolean = false
    ): Parcelable