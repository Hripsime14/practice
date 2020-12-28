package com.cypress.myapplication.fragments.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cypress.myapplication.Resource
import com.cypress.myapplication.Status
import com.cypress.myapplication.backend.UserEntity
import com.cypress.myapplication.modeldatas.model.UserItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersViewModel(private val repo: UsersDataRepo) : ViewModel() {
    private val _liveData = MutableLiveData<Resource<List<UserItem>>>()
    val liveData: LiveData<Resource<List<UserItem>>>
    get() = _liveData


    init {
        getUsers()
    }

    fun getLocalUsers() = repo.getUsersDB()

    fun getUsers() {
        viewModelScope.launch { //returns Job
            makeApiCall()
        }
    }

    private suspend fun makeApiCall() {
        withContext(Dispatchers.IO) {
            _liveData.postValue(Resource(Status.LOADING, null, null))

            try {
                val list = mutableListOf<UserEntity>()
                val result = repo.getUsers() //getting data from server
                result.map {
                    list.add(UserEntity(it.id, it.email, it.username))
                }
                _liveData.postValue(Resource(Status.SUCCESS, null, null))
                repo.setUsersDB(list.toList()) //saving data in local db

            } catch (t: Throwable) {
                withContext(Dispatchers.Main) {
                    _liveData.value = Resource(Status.ERROR, null, t.message)
                }
            }
        }
    }
}