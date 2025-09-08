package com.uyk.satellite.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _satelliteList = MutableStateFlow<List<Satellite>>(emptyList())

    init {
        viewModelScope.launch {
            repository.getSatellites().collect {
                _satelliteList.value = it
            }
        }
    }

    //arama kısmının çalışması için minimum 3 karakter girilmeli
    val filteredList: StateFlow<List<Satellite>> = combine(
        _query.debounce(300),
        _satelliteList
    ) { query, list ->
        if (query.isBlank()) list
        else if (query.length < 3) list
        else list.filter {
            it.name.contains(query.trim(), ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

}