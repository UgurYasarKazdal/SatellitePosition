package com.uyk.satellite.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.uyk.satellite.detail.presentation.SatelliteDetailScreen

fun NavGraphBuilder.addSatelliteDetailComposable() {
    composable(
        route = SatelliteDestinations.SATELLITE_DETAIL_ROUTE,
        arguments = listOf(
            navArgument("satelliteName") { type = NavType.StringType },
            navArgument("satelliteId") { type = NavType.IntType },
        )
    ) { backStackEntry ->
        val satelliteId = backStackEntry.arguments?.getInt("satelliteId") ?: 0
        val satelliteName = backStackEntry.arguments?.getString("satelliteName") ?: ""

        // Detail ekranını oluştur
        SatelliteDetailScreen(satelliteName, satelliteId)
    }
}
