package com.cypress.myapplication

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?) = Resource(Status.SUCCESS, data, null)
        fun <T> loading(data: T?) = Resource(Status.LOADING, data, null)
        fun <T> error(message: String, data: T?) = Resource(Status.ERROR, data, null)
    }
}

enum class Status {
    ERROR, LOADING, SUCCESS
}
