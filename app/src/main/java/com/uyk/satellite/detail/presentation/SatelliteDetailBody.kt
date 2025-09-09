package com.uyk.satellite.detail.presentation

import SatelliteDetailRow
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uyk.satellite.detail.data.SatelliteDetail
import com.uyk.satellite.util.getLineNumber


@Composable
fun DetailBody(
    satelliteName: String,
    satelliteDetail: SatelliteDetail,
    positionContent: @Composable () -> Unit
) {

    Log.e("DetailBody", "where are we: ${getLineNumber()}")

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // İçeriği en üste hizala
        ) {
            Spacer(modifier = Modifier.height(60.dp)) // Üstten boşluk bırak

            Text(
                text = satelliteName, // Geçici isim
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // İlk Uçuş Tarihi
            Text(
                text = satelliteDetail.firstFlightDate,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(48.dp)) // Büyük boşluk

            // Height/Mass
            SatelliteDetailRow(
                label = "Height/Mass:",
                value = satelliteDetail.heightMass
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Cost
            SatelliteDetailRow(
                label = "Cost:",
                value = satelliteDetail.cost // Maliyeti formatla
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Last Position
            positionContent()
        }
    }
}

@Composable
fun SatellitePositionSection(viewModel: SatelliteDetailViewModel) {
    val satellitePosition by viewModel.satellitePosition.collectAsState()
    val position = (satellitePosition as? UiState.Success)?.data ?: "N/A"

    SatelliteDetailRow(label = "Last Position:", position)
}