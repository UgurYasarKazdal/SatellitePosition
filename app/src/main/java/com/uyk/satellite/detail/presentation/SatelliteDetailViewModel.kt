package com.uyk.satellite.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uyk.satellite.detail.data.SatelliteDetail
import com.uyk.satellite.detail.domain.SatelliteDetailRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SatelliteDetailViewModel(val repository: SatelliteDetailRepository) : ViewModel() {
    private val _satelliteDetail =
        MutableStateFlow<UiState<SatelliteDetail>>(UiState.Loading)
    val satelliteDetail: StateFlow<UiState<SatelliteDetail>> = _satelliteDetail

    private val _satellitePosition =
        MutableStateFlow<UiState<String>>(UiState.Loading)
    val satellitePosition: StateFlow<UiState<String>> = _satellitePosition

    fun getSatelliteDetail(satelliteId: String) {
        viewModelScope.launch {

            _satelliteDetail.value = UiState.Loading

            repository.getSatelliteDetail(satelliteId).collect {
                if (it != null) {
                    _satelliteDetail.value = UiState.Success(it)
                } else {
                    _satelliteDetail.value = UiState.Error
                }
            }
        }

    }

    fun startPositionUpdates(satelliteId: String) {
        viewModelScope.launch {
            repository.getSatellitePositions(satelliteId).collect {
                if (it == null) {
                    _satellitePosition.value = UiState.Error
                } else {
                    while (isActive) {
                        it.forEach { positionData ->
                            _satellitePosition.value =
                                UiState.Success("(${positionData.posX},${positionData.posY})")
                            delay(3000)

                        }
                    }

                }
            }
        }
    }
}