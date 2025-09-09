package com.uyk.satellite.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.uyk.satellite.list.presentation.SatelliteScreen

fun NavGraphBuilder.addSatelliteListComposable(navController: NavHostController) {
    composable(SatelliteDestinations.SATELLITE_LIST_ROUTE) {
        SatelliteScreen(
            onSatelliteClick = { satelliteName, satelliteId ->
                navController.navigate(
                    SatelliteDestinations.createDetailRoute(satelliteName, satelliteId)
                )
            }
        )
    }
}
