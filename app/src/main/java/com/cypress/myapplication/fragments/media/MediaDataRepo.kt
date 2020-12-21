package com.cypress.myapplication.fragments.media

import android.app.Activity

class MediaDataRepo(private val mediaLocalDataSource: MediaLocalDataSource) {

    fun getMedias(activity: Activity) = mediaLocalDataSource.getMedias(activity)

    fun getLiveData() = mediaLocalDataSource.getLiveData()
}