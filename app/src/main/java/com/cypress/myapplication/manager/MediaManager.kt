package com.cypress.myapplication.manager

import android.app.Activity
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cypress.myapplication.fragments.media.Mode
import com.cypress.myapplication.modeldatas.model.MediaItem

class MediaManager {
    private val medias = mutableListOf<MediaItem>()
    private val _liveData = MutableLiveData<List<MediaItem>>()
    fun getLiveData(): LiveData<List<MediaItem>> = _liveData

    fun readMedias(activity: Activity) {
        medias.clear()

        val songCols = listOf(
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media._ID
        ).toTypedArray()

        val videoCols = listOf(
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media._ID
        ).toTypedArray()

        val where =  MediaStore.Audio.Media.DATA + " like ? "
        val songResults = activity.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            songCols, where, arrayOf("%practice_mractice%"), MediaStore.Audio.Media.DEFAULT_SORT_ORDER)

        val videoResults = activity.contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            videoCols, where,  arrayOf("%practice_mractice%"), MediaStore.Video.Media.DEFAULT_SORT_ORDER)

        lateinit var mediaItem: MediaItem
        if (songResults != null && songResults.moveToFirst()) {
            do {
                mediaItem = MediaItem(songResults.getString(songResults.getColumnIndex(MediaStore.Audio.Media._ID)),
                    songResults.getString(songResults.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                    songResults.getString(songResults.getColumnIndex(MediaStore.Audio.Media.DATA)),
                    true)
                medias.add(mediaItem)
            } while (songResults.moveToNext())
        }

        if (videoResults != null && videoResults.moveToFirst()) {
            do {
                mediaItem = MediaItem(videoResults.getString(videoResults.getColumnIndex(MediaStore.Video.Media._ID)),
                    videoResults.getString(videoResults.getColumnIndex(MediaStore.Video.Media.TITLE)),
                    videoResults.getString(videoResults.getColumnIndex(MediaStore.Video.Media.DATA)),
                    false)
                medias.add(mediaItem)
            } while (videoResults.moveToNext())
        }

        songResults?.close()
        videoResults?.close()
        Log.d("ghgh", "readMedias: ${medias.size}")
        _liveData.value = medias
    }
}