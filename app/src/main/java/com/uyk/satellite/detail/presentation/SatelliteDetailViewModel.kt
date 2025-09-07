package com.uyk.satellite.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uyk.satellite.detail.data.SatelliteDetail
import com.uyk.satellite.detail.domain.SatelliteDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SatelliteDetailViewModel(val repository: SatelliteDetailRepository) : ViewModel() {
    private val _uiState =
        MutableStateFlow<UiState<SatelliteDetail>>(UiState.Loading)
    val uiState: StateFlow<UiState<SatelliteDetail>> = _uiState

    fun getSatelliteDetail(satelliteId: String) {
        viewModelScope.launch {

            _uiState.value = UiState.Loading

            repository.getMergedSatelliteData(satelliteId).collect {
                if (it != null) {
                    _uiState.value = UiState.Success(it)
                } else {
                    _uiState.value = UiState.Error
                }
            }
        }

    }
}