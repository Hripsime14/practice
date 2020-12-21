package com.cypress.myapplication.fragments.media

import android.app.Activity
import com.cypress.myapplication.manager.MediaManager

class MediaLocalDataSource(private val mediaManager: MediaManager) {

    fun getMedias(activity: Activity) = mediaManager.readMedias(activity)

    fun getLiveData() = mediaManager.getLiveData()

}