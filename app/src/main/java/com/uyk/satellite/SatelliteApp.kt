package com.uyk.satellite

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.uyk.satellite.core.navigation.SatelliteNavGraph

@Composable
fun SatelliteApp() {
    val navController = rememberNavController()

    SatelliteNavGraph(navController = navController)

}