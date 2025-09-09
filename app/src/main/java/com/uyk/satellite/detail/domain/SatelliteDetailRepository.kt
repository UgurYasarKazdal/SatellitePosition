package com.uyk.satellite.detail.domain

import android.content.Context
import android.util.Log
import com.uyk.satellite.detail.data.PositionData
import com.uyk.satellite.detail.data.PositionsResponseDto
import com.uyk.satellite.detail.data.SatelliteDetail
import com.uyk.satellite.detail.data.SatelliteDetailCache
import com.uyk.satellite.detail.data.SatelliteDetailDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

class SatelliteDetailRepository(private val context: Context) {

    fun getSatelliteDetail(satelliteId: String): Flow<SatelliteDetail?> = flow {
        if (checkSatelliteDetailCached(satelliteId)) {
            emit(getSatelliteDetailOnce(satelliteId))
        } else {
            val json = context.assets.open("satellite_detail.json")
                .bufferedReader()
                .use { it.readText() }
            val dtoList =
                Json.decodeFromString<List<SatelliteDetailDto>>(json)
                    .find { it.id.toString() == satelliteId }
            if (dtoList != null) {
                SatelliteDetailCache.putIfAbsent(satelliteId, dtoList.toDomain())
                emit(dtoList.toDomain())
            }

        }
    }.catch { e ->
        Log.e("SatelliteDetailRepository", "Serialization error", e)
    }.flowOn(Dispatchers.IO)

    private fun checkSatelliteDetailCached(id: String) = SatelliteDetailCache.get(id) != null

    private fun getSatelliteDetailOnce(id: String): SatelliteDetail {
        return SatelliteDetailCache.get(id)!!
    }

    // Pozisyonları okuyan ve bir Flow döndüren fonksiyon
    fun getSatellitePositions(satelliteId: String): Flow<List<PositionData>?> = flow {
        val json = context.assets.open("positions.json")
            .bufferedReader()
            .use { it.readText() }

        val dtoList = Json.decodeFromString<PositionsResponseDto>(json)

        emit(dtoList.toDomain(satelliteId))
    }.catch { e ->
        Log.e("SatelliteRepository", "Serialization error while parsing positions.json", e)
    }.flowOn(Dispatchers.IO) // IO thread'inde çalıştır


}