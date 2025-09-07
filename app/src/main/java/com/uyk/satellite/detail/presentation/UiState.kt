package com.uyk.satellite.detail.presentation

// UI durumunu temsil eden sealed class
sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data object Error : UiState<Nothing>()
}
