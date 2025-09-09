package com.uyk.satellite.detail.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SatelliteDetailScreen(satelliteName: String, satelliteId: Int) {
    val satelliteDetailViewModel = koinViewModel<SatelliteDetailViewModel>()

    val satelliteDetail by satelliteDetailViewModel.satelliteDetail.collectAsState()
    val satellitePosition by satelliteDetailViewModel.satellitePosition.collectAsState()

    satelliteDetailViewModel.getSatelliteDetail(satelliteId = satelliteId.toString())

    LaunchedEffect(satelliteId) {
        satelliteDetailViewModel.getSatelliteDetail(satelliteId = satelliteId.toString())
        satelliteDetailViewModel.startPositionUpdates(satelliteId.toString())
    }

    when (satelliteDetail) {
        is UiState.Loading -> {
            // Yüklenme durumunda gösterilecek UI
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            val detail = (satelliteDetail as UiState.Success).data
            val position = (satellitePosition as? UiState.Success)?.data ?: "N/A"

            DetailBody(
                satelliteName = satelliteName,
                satelliteDetail = detail,
                satellitePosition = position
            )
        }

        is UiState.Error -> {
            // Hata durumunda gösterilecek UI
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Bir hata oluştu. Lütfen tekrar deneyin.")
            }
        }

    }

}