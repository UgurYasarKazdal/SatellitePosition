package com.uyk.satellite.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uyk.satellite.list.data.Satellite
import com.uyk.satellite.list.domain.SatelliteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SatelliteViewModel(repository: SatelliteRepository) : ViewModel() {

    private val _satelliteList = MutableStateFlow<List<Satellite>>(emptyList())
    val satelliteList: StateFlow<List<Satellite>> = _satelliteList

    init {
        viewModelScope.launch {
            repository.getSatellites().collect {
                _satelliteList.value = it
            }
        }
    }
}