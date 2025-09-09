package com.uyk.satellite.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun SatelliteNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SatelliteDestinations.SATELLITE_LIST_ROUTE
    ) {
        addSatelliteListComposable(navController)
        addSatelliteDetailComposable()
    }
}