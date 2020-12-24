package com.cypress.myapplication.fragments.users.albums

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cypress.myapplication.Resource
import com.cypress.myapplication.Status
import com.cypress.myapplication.backend.AlbumEntity
import com.cypress.myapplication.backend.PhotoEntity
import com.cypress.myapplication.fragments.photos.PhotoDataRepo
import com.cypress.myapplication.modeldatas.model.AlbumItem
import com.cypress.myapplication.modeldatas.model.PhotoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumsViewModel(userId: Int, private val repo: AlbumsDataRepo, private val photoRepo: PhotoDataRepo): ViewModel() {
    private lateinit var job: Job
    private var collected = false
    private val photosList = mutableListOf<PhotoEntity>()
    //albums
    private val _albumsLiveData = MutableLiveData<Resource<List<AlbumItem>>>()
    val albumsLiveData: LiveData<Resource<List<AlbumItem>>>
    get() =  _albumsLiveData

    //photos
    private val _photoLiveData = MutableLiveData<Resource<List<PhotoItem>>>()
    val photoLiveData: LiveData<Resource<List<PhotoItem>>>
        get() =  _photoLiveData

    init {
        getAlbums(userId)
    }

    fun getAlbums(id: Int) {
        job = viewModelScope.launch {
            makeApiCall(id)
        }
    }

    private suspend fun makeApiCall(id: Int) {
        withContext(Dispatchers.IO) {
            _albumsLiveData.postValue(Resource(Status.LOADING, null, null))
            try {
                val list = mutableListOf<AlbumEntity>()
                val result = repo.getRemoteAlbums(id)
                for (i in result.indices) {
                    if (i == result.size - 1) {
                        collected = true
                    }
                    makeApiCallPhotos(result[i].id)
                }

                result.map {
                    list.add(AlbumEntity(it.id, it.title))
                }
                repo.deleteAlbums()
                repo.setLocalAlbums(list.toList())
            } catch (t: Throwable) {
                Log.d("bbbbbbbbbbbbbbbb", "makeApiCall: rrrrrr")
                _albumsLiveData.postValue(Resource(Status.ERROR, null, t.message))
            }
        }
    }

    fun clearRoom() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.deleteAlbums()
            }
        }
    }

    private suspend fun makeApiCallPhotos(id: Int) {
        _photoLiveData.postValue(Resource(Status.LOADING, null, null))
        try {
            val result = photoRepo.getRemotePhotos(id)
            result.map {
                photosList.add(PhotoEntity(it.id, it.albumId, it.title, it.url, it.thumbnailUrl))
            }
            if (collected) {
//                photoRepo.deletePhotos()
                photoRepo.setLocalPhotos(photosList.toList())
                collected = false
            }

        } catch (t: Throwable) {
            _photoLiveData.postValue(Resource(Status.ERROR, null, t.message))
        }
    }

    fun clearPhotoList() {
        photosList.clear()
    }

    fun getLocalAlbums(): LiveData<List<AlbumEntity>> {
        return repo.getLocalAlbums()
    }

    fun getLocalPhotos(): LiveData<List<PhotoEntity>> {
        return photoRepo.getLocalPhotos()
    }

    override fun onCleared() {
        super.onCleared()
        if (!job.isCancelled) job.cancel()
    }
}