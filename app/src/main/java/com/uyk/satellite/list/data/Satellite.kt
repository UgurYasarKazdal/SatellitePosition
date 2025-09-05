package com.uyk.satellite.list.data

import kotlinx.serialization.Serializable

enum class SatelliteStatus {
    ACTIVE,
    PASSIVE
}

@Serializable
data class Satellite(
    val id: Int,
    val name: String,
    val status: SatelliteStatus
)

@Serializable
data class SatelliteDto(
    val id: Int,
    val name: String,
    val active: Boolean
) {
    fun toDomain(): Satellite = Satellite(
        id = id,
        name = name,
        status = if (active) SatelliteStatus.ACTIVE else SatelliteStatus.PASSIVE
    )
}