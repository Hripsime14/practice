package com.cypress.myapplication.fragments.users.albums

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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumsViewModel(userId: Int, private val repo: AlbumsDataRepo, private val photoRepo: PhotoDataRepo): ViewModel() {
    private var collected = false
    private val photosList = mutableListOf<PhotoEntity>()
    //albums
    private val _liveData = MutableLiveData<Resource<List<AlbumItem>>>()
    val liveData: LiveData<Resource<List<AlbumItem>>>
    get() =  _liveData

    //photos
    private val _photoLiveData = MutableLiveData<Resource<List<PhotoItem>>>()
    val photoLiveData: LiveData<Resource<List<PhotoItem>>>
        get() =  _photoLiveData

    init {
        getAlbums(userId)
    }

    private fun getAlbums(id: Int) {
        viewModelScope.launch {
            makeApiCall(id)
        }
    }

    private suspend fun makeApiCall(id: Int) {
        withContext(Dispatchers.IO) {
            _liveData.postValue(Resource(Status.LOADING, null, null))
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
                _liveData.postValue(Resource(Status.ERROR, null, t.message))
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
            photoRepo.deletePhotos()
            //TODO: I guess the photos should not be passed to local db(glide does so under the hood)
            if (collected) photoRepo.setLocalPhotos(photosList.toList())

        } catch (t: Throwable) {
            _photoLiveData.postValue(Resource(Status.ERROR, null, t.message))
        }
    }


    fun getLocalAlbums(): LiveData<List<AlbumEntity>> {
        return repo.getLocalAlbums()
    }

    fun getLocalPhotos(): LiveData<List<PhotoEntity>> {
        return photoRepo.getLocalPhotos()
    }
}