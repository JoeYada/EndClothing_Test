package com.example.endclothing.utils

class LoadState(val status: Status, val message: String? = null) {

    companion object{
        val LOADING = LoadState(Status.LOADING)
        val SUCCESS = LoadState(Status.SUCCESS)
        fun error(message: String?) = LoadState(Status.FAILED, message)
    }
    enum class Status{
        LOADING,
        SUCCESS,
        FAILED
    }
}