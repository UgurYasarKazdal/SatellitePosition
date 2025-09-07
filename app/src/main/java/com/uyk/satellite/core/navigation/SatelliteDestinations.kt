package com.uyk.satellite.core.navigation

object SatelliteDestinations {
    const val SATELLITE_LIST_ROUTE = "satellites"
    const val SATELLITE_DETAIL_ROUTE = "satellite_detail/{satelliteName}/{satelliteId}"

    fun createDetailRoute(satelliteName: String, satelliteId: Int): String {
        return "satellite_detail/$satelliteName/$satelliteId"
    }
}