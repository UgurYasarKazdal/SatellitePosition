package com.uyk.satellite.list.domain

import android.content.Context
import android.util.Log
import com.uyk.satellite.list.data.Satellite
import com.uyk.satellite.list.data.SatelliteDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.Json

class SatelliteRepository(private val context: Context) {
    fun getSatellites(): Flow<List<Satellite>> = flow {
        val json = context.assets.open("satellite_list.json")
            .bufferedReader()
            .use { it.readText() }

        val dtoList = Json.decodeFromString<List<SatelliteDto>>(json)
        emit(dtoList.map { it.toDomain() })
    }.catch { e ->
        emit(emptyList())
        Log.e("SatelliteRepository", "Serialization error", e)
    }.flowOn(Dispatchers.IO)
}