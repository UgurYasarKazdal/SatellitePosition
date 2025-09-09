package com.uyk.satellite.detail.data

import com.uyk.satellite.util.formatCost
import com.uyk.satellite.util.formatFirstFlightDate
import kotlinx.serialization.Serializable

data class SatelliteDetail(
    val firstFlightDate: String,
    val heightMass: String,
    val cost: String
)

data class PositionData(
    val posX: Double,
    val posY: Double
)

@Serializable
data class SatellitePositionsDto(
    val id: String,
    val positions: List<PositionDto>
)

@Serializable
data class PositionDto(
    val posX: Double,
    val posY: Double
)

@Serializable
data class PositionsResponseDto(
    val list: List<SatellitePositionsDto>
)

@Serializable
data class SatelliteDetailDto(
    val id: Int,
    val cost_per_launch: Long,
    val first_flight: String, // Format: DD.MM.YYYY
    val height: Int,
    val mass: Int

) {
    fun toDomain(): SatelliteDetail = SatelliteDetail(
        firstFlightDate = formatFirstFlightDate(first_flight),
        heightMass = "$height/$mass",
        cost = formatCost(cost_per_launch),
    )
}