package com.uyk.satellite.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uyk.satellite.detail.presentation.UiState
import com.uyk.satellite.list.data.Satellite
import com.uyk.satellite.list.domain.SatelliteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SatelliteViewModel(repository: SatelliteRepository) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _satelliteList = MutableStateFlow<UiState<List<Satellite>>>(UiState.Loading)
    val filteredList: StateFlow<UiState<List<Satellite>>> = combine(
        _query.debounce(300),
        _satelliteList
    ) { query, uiState ->
        when (uiState) {
            is UiState.Success -> {
                val originalList = uiState.data
                val filtered = if (query.isBlank() || query.length < 3) {
                    originalList
                } else {
                    originalList.filter {
                        it.name.contains(query.trim(), ignoreCase = true)
                    }
                }
                UiState.Success(filtered)
            }

            is UiState.Error -> UiState.Error
            UiState.Loading -> UiState.Loading
        }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    init {
        viewModelScope.launch {
            repository.getSatellites().collect {
                _satelliteList.value = UiState.Success(it)
            }
        }
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

}