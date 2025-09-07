package com.uyk.satellite.detail.domain

import android.content.Context
import android.util.Log
import com.uyk.satellite.detail.data.PositionData
import com.uyk.satellite.detail.data.PositionDto
import com.uyk.satellite.detail.data.PositionsResponseDto
import com.uyk.satellite.detail.data.SatelliteDetail
import com.uyk.satellite.detail.data.SatelliteDetailDto
import com.uyk.satellite.detail.data.SatellitePositionsDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.serialization.json.Json

class SatelliteDetailRepository(private val context: Context) {
    fun getSatelliteDetail(satelliteId: String): Flow<SatelliteDetail?> = flow {
        val json = context.assets.open("satellite_detail.json")
            .bufferedReader()
            .use { it.readText() }

        val dtoList =
            Json.decodeFromString<List<SatelliteDetailDto>>(json).find { it.id.toString() == satelliteId }
        emit(dtoList?.toDomain())
    }.catch { e ->
        Log.e("SatelliteDetailRepository", "Serialization error", e)
    }.flowOn(Dispatchers.IO)

    // Pozisyonları okuyan ve bir Flow döndüren fonksiyon
    fun getSatellitePositions(satelliteId: String): Flow<List<PositionData>?> = flow {
        val json = context.assets.open("positions.json")
            .bufferedReader()
            .use { it.readText() }

        val dtoList = Json.decodeFromString<PositionsResponseDto>(json)

        emit(dtoList.list.toDomain(satelliteId))
    }.catch { e ->
        Log.e("SatelliteRepository", "Serialization error while parsing positions.json", e)
    }.flowOn(Dispatchers.IO) // IO thread'inde çalıştır


    // Domain modellerini DTO'lardan dönüştürme fonksiyonları
    fun List<SatellitePositionsDto>.toDomain(satelliteId: String): List<PositionData>? {
        return this.find { it.id == satelliteId }?.positions?.map { it.toDomain() }
    }


    fun PositionDto.toDomain(): PositionData {
        return PositionData(this.posX, this.posY)
    }


    // İki akışı birleştirip son pozisyon bilgisiyle beraber detay bilgilerini döndüren fonksiyon
    fun getMergedSatelliteData(satelliteId: String): Flow<SatelliteDetail?> {
        val detailsFlow = getSatelliteDetail(satelliteId)
        val positionsFlow = getSatellitePositions(satelliteId)

        return detailsFlow.zip(positionsFlow) { details, positions ->
            details?.copy(
                lastPosition = "(${positions?.last()?.posX},${positions?.last()?.posY})"
            )
        }
    }

}