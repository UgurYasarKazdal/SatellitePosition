package com.uyk.satellite.detail.presentation

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val throwable: Throwable? = null) : Resource<Nothing>()
}