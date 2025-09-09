package com.uyk.satellite.detail.presentation

import SatelliteDetailRow
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uyk.satellite.detail.data.SatelliteDetail

@Composable
fun DetailBody(
    satelliteName: String,
    satelliteDetail: SatelliteDetail,
    satellitePosition: String
) {
    if (satelliteDetail == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Uydu detayları bulunamadı.", color = Color.Gray, fontSize = 18.sp)
        }
        return
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // İçeriği en üste hizala
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
                fontSize = 18.sp,
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
            SatelliteDetailRow(
                label = "Last Position:",
                value = satellitePosition
            )
        }
    }
}