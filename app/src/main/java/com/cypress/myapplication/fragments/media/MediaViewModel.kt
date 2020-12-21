package com.cypress.myapplication.fragments.media

import android.app.Activity
import androidx.lifecycle.ViewModel

class MediaViewModel(private val mediaDataRepo: MediaDataRepo): ViewModel() {

    fun getMedias(activity: Activity) = mediaDataRepo.getMedias(activity)

    fun getLiveData() = mediaDataRepo.getLiveData()
}