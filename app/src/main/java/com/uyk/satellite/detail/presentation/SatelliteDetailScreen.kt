package com.uyk.satellite.detail.presentation

import android.util.Log
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
import com.uyk.satellite.util.getLineNumber
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SatelliteDetailScreen(satelliteName: String, satelliteId: Int) {
    val satelliteDetailViewModel = koinViewModel<SatelliteDetailViewModel>()

    val satelliteDetail by satelliteDetailViewModel.satelliteDetail.collectAsState()

    LaunchedEffect(satelliteId) {
        satelliteDetailViewModel.getSatelliteDetail(satelliteId = satelliteId.toString())
        satelliteDetailViewModel.startPositionUpdates(satelliteId.toString())
    }

    Log.e("SatelliteDetailScreen", "where are we: ${getLineNumber()}")

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

            //logları takip etmek için getLineNumber kullanıldı.
            Log.e("SatelliteDetailScreen", "where are we: ${getLineNumber()}")

            DetailBody(
                satelliteName = satelliteName,
                satelliteDetail = detail,
                positionContent = {
                    //pozisyon güncellemesinde tüm ekran yeniden çizilmesin pozsiyon bilgisi ayrı bir composeable içine alındı
                    SatellitePositionSection(satelliteDetailViewModel)
                }
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