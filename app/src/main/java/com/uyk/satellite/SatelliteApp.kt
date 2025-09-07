package com.uyk.satellite

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.uyk.satellite.core.navigation.SatelliteDestinations
import com.uyk.satellite.detail.presentation.SatelliteDetailScreen

@Composable
fun SatelliteApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SatelliteDestinations.SATELLITE_LIST_ROUTE
    ) {
        // Liste ekranı
        composable(SatelliteDestinations.SATELLITE_LIST_ROUTE) {
            SatelliteScreen(
                onSatelliteClick = { satelliteName, satelliteId ->

                    Log.e("DETAİL", satelliteId.toString())

                    navController.navigate(
                        SatelliteDestinations.createDetailRoute(
                            satelliteName,
                            satelliteId
                        )
                    )
                }
            )
        }

        // Detay ekranı
        composable(
            route = SatelliteDestinations.SATELLITE_DETAIL_ROUTE,
            arguments = listOf(
                navArgument("satelliteName") { type = NavType.StringType },
                navArgument("satelliteId") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            val satelliteId = backStackEntry.arguments?.getInt("satelliteId") ?: 0
            val satelliteName = backStackEntry.arguments?.getString("satelliteName") ?: ""
            SatelliteDetailScreen(satelliteName, satelliteId)

        }
    }
}